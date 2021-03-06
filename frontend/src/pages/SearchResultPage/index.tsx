import { useContext, useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router';

import API from 'src/apis/requests';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';
import { ERROR_MESSAGE, PATH } from 'src/constants';
import APPLICATION_ERROR_CODE from 'src/constants/applicationErrorCode';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import usePageTitle from 'src/hooks/usePageTitle';
import NoSearchResults from './NoSearchResults';
import { Container, SearchResult } from './styles';

const SearchResultPage = () => {
  const history = useHistory();
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const words = new URLSearchParams(location.search).get('words') ?? '';
  const params = new URLSearchParams({ keyword: words });

  useEffect(() => {
    if (words === '') {
      history.push(PATH.SEARCH);
      return;
    }
  }, [words]);

  usePageTitle(`${words} 검색 결과`);

  const {
    data: resultsData,
    isLoading,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery(
    'search-results',
    ({ pageParam = 1 }) => API.getSearchResults({ params, page: pageParam }),
    {
      retry: 0,
      getNextPageParam: ({ pageInfo }) => {
        const { currentPage, lastPage } = pageInfo;

        return currentPage < lastPage ? currentPage + 1 : undefined;
      },
      onError: (error: Request.Error) => {
        if (
          error.code === APPLICATION_ERROR_CODE.NETWORK_ERROR ||
          error.code === APPLICATION_ERROR_CODE.INTERNAL_SERVER_ERROR
        ) {
          history.push({
            pathname: PATH.ERROR_PAGE,
            state: { code: error.code },
          });
        }

        setSnackbarMessage?.({
          type: 'ERROR',
          message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT,
        });
      },
    }
  );

  const searchResult = resultsData?.pages?.map((page) => page.data).flat() ?? [];
  const [
    {
      pageInfo: { totalSize },
    },
  ] = resultsData?.pages ?? [{ pageInfo: { totalSize: 0 } }];

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useInfinityScroll({ target: observerTargetRef, fetchNextPage, hasNextPage });

  const getSkeletonUI = () => (
    <>
      <Skeleton type="TEXT" size="MEDIUM" width="14rem" margin="1rem 2rem" />
      <List>
        <ListItemSkeleton count={7} />
      </List>
    </>
  );

  return (
    <Container>
      <NavigationHeader title={`검색결과 ${totalSize || 0}건`} />

      <section>
        {isLoading ? (
          getSkeletonUI()
        ) : searchResult?.length ? (
          <>
            <SearchResult>
              <strong>{words}</strong>로 검색한 결과입니다.
            </SearchResult>
            <List count={searchResult?.length || 0}>
              {searchResult?.map((item: Drink.Item) => (
                <ListItem
                  key={item?.id}
                  itemId={item?.id}
                  imageUrl={item?.imageResponse.small}
                  title={item?.name}
                  description={`도수: ${item?.alcoholByVolume}%`}
                  preferenceType={
                    item?.preferenceRate ? 'MY' : item?.expectedPreference ? 'EXPECTED' : 'AVG'
                  }
                  preferenceRate={
                    item?.preferenceRate || item?.expectedPreference || item?.preferenceAvg
                  }
                />
              ))}
            </List>
          </>
        ) : (
          <NoSearchResults search={words || ''} />
        )}

        <InfinityScrollPoll ref={observerTargetRef} />
      </section>
    </Container>
  );
};

export default SearchResultPage;
