import { useEffect, useState, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { RouteComponentProps } from 'react-router-dom';
import API from 'src/apis/requests';
import Arrow from 'src/components/@shared/Arrow/Arrow';

import Header from 'src/components/@shared/Header/Header';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import { PATH } from 'src/constants';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import { categories } from '../SearchPage';

import { Container, Title, Section } from './styles';

const SearchResultPage = ({ history, location }: RouteComponentProps) => {
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const words = new URLSearchParams(location.search).get('words');
  const categoryKey = new URLSearchParams(location.search).get('category');

  const categoryName =
    categoryKey && categories.find((category) => category.key === categoryKey)?.name;

  const [searchResultList, setSearchResultList] = useState<SearchResult.SearchResultList>([]);

  const { isLoading, fetchNextPage, hasNextPage } = useInfiniteQuery(
    'search-results',
    ({ pageParam = 1 }) =>
      API.getSearchResult({ words: words ?? '', category: categoryKey ?? '', page: pageParam }),
    {
      retry: 0,
      getNextPageParam: ({ pageInfo }) => {
        const { currentPage, lastPage } = pageInfo;

        return currentPage < lastPage ? currentPage + 1 : undefined;
      },
      onSuccess: (data) => {
        const { pages } = data;
        setSearchResultList(pages?.map((page) => page.data).flat());
      },
    }
  );

  useEffect(() => {
    window.scrollTo(0, 0);

    return () => setSearchResultList([]);
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
          <h1>검색결과 {searchResultList?.length || 0}건</h1>
        </Title>
      </Header>

      <Section>
        {isLoading ? (
          <p>Loading..</p>
        ) : (
          <>
            <h2>
              <strong>{words || categoryName}</strong>로 검색한 결과입니다.
            </h2>

            <List count={searchResultList?.length || 0}>
              {searchResultList?.map((item: SearchResult.SearchResultItem) => (
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
        )}
      </Section>
    </Container>
  );
};

export default SearchResultPage;
