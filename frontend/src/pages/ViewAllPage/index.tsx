import { useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import { PATH } from 'src/constants';
import { Container, InfinityScrollPoll } from './ViewAllPage.styles';

const ViewAllPage = () => {
  const history = useHistory();
  const infinityPollRef = useRef<HTMLDivElement>(null);

  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery(
    'drinks',
    ({ pageParam = 1 }) => API.getDrinks({ page: pageParam }),
    {
      getNextPageParam: ({ pageInfo }) => {
        return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
      },
    }
  );
  const drinks = data?.pages?.map((page) => page.data).flat() ?? [];

  const onMoveToDrinkDetail = (id: number) => () => {
    history.push(`${PATH.DRINKS}/${id}`);
  };

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting && hasNextPage) {
        fetchNextPage();
      }
    });
  });

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    if (infinityPollRef.current) {
      observer.observe(infinityPollRef.current);
    }
  }, [infinityPollRef.current]);

  return (
    <Container>
      <NavigationHeader title="전체보기" />
      <List count={drinks?.length}>
        {drinks?.map((item: Drink.Item) => (
          <ListItem
            key={item?.id}
            imageUrl={item?.imageUrl}
            title={item?.name}
            description={`도수: ${item?.alcoholByVolume}%`}
            onClick={onMoveToDrinkDetail(item?.id)}
          />
        ))}
        {isFetching && <ListItemSkeleton count={7} />}
      </List>
      <InfinityScrollPoll ref={infinityPollRef} />
    </Container>
  );
};

export default ViewAllPage;
