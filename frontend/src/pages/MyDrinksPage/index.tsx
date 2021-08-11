import { useEffect, useRef } from 'react';
import { useHistory } from 'react-router-dom';
import { useInfiniteQuery } from 'react-query';

import API from 'src/apis/requests';
import Grid from 'src/components/@shared/Grid/Grid';
import MyDrinkItem from './MyDrinkItem';

import { Header, Container } from './styles';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import Arrow from 'src/components/@shared/Arrow/Arrow';
import PersonalDrinkItemSkeleton from 'src/components/@shared/Skeleton/PersonalDrinkItemSkeleton';

const MyDrinksPage = () => {
  const history = useHistory();

  const {
    data: { pages } = {},
    fetchNextPage,
    hasNextPage,
    isFetching,
  } = useInfiniteQuery(
    'my-drinks',
    ({ pageParam = 1 }) => API.getPersonalDrinks({ page: pageParam, size: 10 }),
    {
      getNextPageParam: ({ pageInfo }) => {
        const { currentPage, lastPage } = pageInfo;

        return currentPage < lastPage ? currentPage + 1 : undefined;
      },
    }
  );

  const observerTargetRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
  useInfinityScroll({ target: observerTargetRef, fetchNextPage, hasNextPage });

  const { matches } = window.matchMedia('screen and (max-width:380px)');

  const myDrinks = pages?.map((page) => page.data).flat();

  const onMoveToPrevPage = () => history.goBack();

  return (
    <>
      <Header>
        <button type="button" onClick={onMoveToPrevPage}>
          <Arrow size="0.7rem" borderWidth="2px" dir="LEFT" />
        </button>
        <h2>선호도를 남긴 술</h2>
      </Header>

      <Container>
        <Grid col={matches ? 2 : 3} rowGap="1.5rem" title="선호도를 남긴 술" justifyItems="center">
          {myDrinks?.map((myDrink: Drink.PersonalDrinkItem) => (
            <MyDrinkItem key={myDrink.id} size={matches ? 'X_LARGE' : 'LARGE'} drink={myDrink} />
          ))}
          {isFetching && (
            <PersonalDrinkItemSkeleton count={8} size={matches ? 'X_LARGE' : 'LARGE'} />
          )}
        </Grid>
        <InfinityScrollPoll ref={observerTargetRef} />
      </Container>
    </>
  );
};

export default MyDrinksPage;
