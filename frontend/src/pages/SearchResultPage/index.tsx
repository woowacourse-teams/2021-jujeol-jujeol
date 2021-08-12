import { useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { RouteComponentProps } from 'react-router-dom';
import API from 'src/apis/requests';
import Arrow from 'src/components/@shared/Arrow/Arrow';

import Header from 'src/components/@shared/Header/Header';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import { PATH } from 'src/constants';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import { categories } from '../SearchPage';
import NoSearchResults from './NoSearchResults';

import { Container, Title, ResultHeading } from './styles';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';

const SearchResultPage = ({ history, location }: RouteComponentProps) => {
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const words = new URLSearchParams(location.search).get('words') ?? '';
  const categoryKey = new URLSearchParams(location.search).get('category') ?? '';

  const params = new URLSearchParams({ search: words, category: categoryKey });
  params.forEach((value, key) => {
    if (value === '') {
      params.delete(key);
    }
  });

  const categoryName =
    categoryKey && categories.find((category) => category.key === categoryKey)?.name;

  const {
    data: resultsData,
    isLoading,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery(
    'search-results',
    ({ pageParam = 1 }) => API.getDrinks({ params, page: pageParam }),
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

  const onMoveToPrevPage = () => history.goBack();

  return (
    <Container>
      <Header>
        <Title>
          <button type="button" onClick={onMoveToPrevPage}>
            <Arrow size="0.7rem" borderWidth="2px" dir="LEFT" />
          </button>
          <h1>검색결과 {totalSize || 0}건</h1>
        </Title>
      </Header>

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
            <ResultHeading>
              <strong>{words || categoryName}</strong>로 검색한 결과입니다.
            </ResultHeading>
            <List count={searchResult?.length || 0}>
              {searchResult?.map((item: Drink.Item) => (
                <ListItem
                  key={item?.id}
                  imageUrl={item?.imageUrl}
                  title={item?.name}
                  description={`도수: ${item?.alcoholByVolume}%`}
                  onClick={() => {
                    history.push(`${PATH.DRINKS}/${item?.id}`);
                  }}
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
