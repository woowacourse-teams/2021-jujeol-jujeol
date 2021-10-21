import { useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { RouteComponentProps } from 'react-router-dom';

import API from 'src/apis/requests';

import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import { PATH } from 'src/constants';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import { categories } from '../SearchPage';
import NoSearchResults from './NoSearchResults';

import { Container, SearchResult } from './styles';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import usePageTitle from 'src/hooks/usePageTitle';

const SearchResultPage = ({ location }: RouteComponentProps) => {
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const words = new URLSearchParams(location.search).get('words') ?? '';
  const categoryKey = new URLSearchParams(location.search).get('category') ?? '';
  const categoryName =
    categoryKey && categories.find((category) => category.key === categoryKey)?.name;

  const params = new URLSearchParams({ keyword: words, category: categoryKey });

  params.forEach((value, key) => {
    if (value === '') {
      params.delete(key);
    }
  });

  usePageTitle(`${words || categoryName} 검색 결과`);

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

  return (
    <Container>
      <NavigationHeader title={`검색결과 ${totalSize || 0}건`} />

      <section>
        {isLoading ? (
          <>
            <Skeleton type="TEXT" size="MEDIUM" width="14rem" margin="1rem 2rem" />
            <List>
              <ListItemSkeleton count={7} />
            </List>
          </>
        ) : searchResult?.length ? (
          <>
            <SearchResult>
              <strong>{words || categoryName}</strong>로 검색한 결과입니다.
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
            <InfinityScrollPoll ref={observerTargetRef} />
          </>
        ) : (
          <NoSearchResults search={words || categoryName || ''} />
        )}
      </section>
    </Container>
  );
};

export default SearchResultPage;
