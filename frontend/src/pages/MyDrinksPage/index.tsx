import { useEffect, useRef } from 'react';
import { useHistory } from 'react-router-dom';
import { useInfiniteQuery } from 'react-query';

import API from 'src/apis/requests';
import ArrowButton from 'src/components/@shared/ArrowButton/ArrowButton';
import Grid from 'src/components/@shared/Grid/Grid';
import MyDrinkItem from './MyDrinkItem';

import { Header, Container } from './styles';
import useIntersectionObserver from 'src/hooks/useIntersectionObserver';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';

const MyDrinks = () => {
  const history = useHistory();

  const {
    data: { pages } = {},
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery(
    'my-drinks',
    ({ pageParam = 1 }) => API.getPersonalDrinks({ page: pageParam, size: 10 }),
    {
      retry: 0,
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
  useIntersectionObserver({ target: observerTargetRef, fetchNextPage, hasNextPage });

  const { matches } = window.matchMedia('screen and (max-width:380px)');

  const myDrinks = pages?.map((page) => page.data).flat();

  return (
    <>
      <Header>
        <ArrowButton size="0.7rem" borderWidth="2px" dir="LEFT" onClick={() => history.goBack()} />
        <h2>내가 마신 술</h2>
      </Header>

      <Container>
        <Grid col={matches ? 2 : 3} rowGap="1.5rem">
          {myDrinks?.map((myDrink: MyDrink.MyDrinkItem) => (
            <MyDrinkItem key={myDrink.id} size={matches ? 'X_LARGE' : 'LARGE'} drink={myDrink} />
          ))}
        </Grid>
        <InfinityScrollPoll />
      </Container>
    </>
  );
};

export default MyDrinks;
