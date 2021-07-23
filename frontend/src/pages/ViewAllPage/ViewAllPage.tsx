import { useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import Header from 'src/components/@shared/Header/Header';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import { PATH } from 'src/constants';
import { InfinityScrollPoll, Title } from './ViewAllPage.styles';

const ViewAllPage = () => {
  const history = useHistory();
  const scrollAreaRef = useRef<HTMLDivElement>(null);
  const infinityPollRef = useRef<HTMLDivElement>(null);

  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery<{
    data: ItemList.Drinks[];
    pageInfo: { currentPage: number; lastPage: number; countPerPage: number; totalSize: number };
  }>('drinks', ({ pageParam = 1 }) => API.getDrinks({ page: pageParam }), {
    getNextPageParam: ({ pageInfo }) => {
      return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
    },
  });
  const drinks = data?.pages?.map((page) => page.data).flat() ?? [];

  const goBack = () => {
    history.goBack();
  };

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && hasNextPage) {
          fetchNextPage();
        }
      });
    },
    { root: scrollAreaRef.current }
  );

  useEffect(() => {
    window.scrollTo(0, 0);

    if (infinityPollRef.current) {
      observer.observe(infinityPollRef.current);
    }
  }, [infinityPollRef.current]);

  return (
    <div style={{ position: 'relative' }} ref={scrollAreaRef}>
      <Header>
        <Title>
          <button type="button" onClick={goBack}>
            {'<'}
          </button>
          <h1>전체보기</h1>
        </Title>
      </Header>
      <List count={drinks?.length}>
        {drinks?.map((item: ItemList.Drinks) => (
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
      <InfinityScrollPoll ref={infinityPollRef} />
    </div>
  );
};

export default ViewAllPage;
