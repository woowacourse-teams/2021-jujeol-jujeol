import { useContext, useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router-dom';

import API from 'src/apis/requests';
import Grid from 'src/components/@shared/Grid/Grid';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import PersonalDrinkItemSkeleton from 'src/components/Skeleton/PersonalDrinkItemSkeleton';
import { APPLICATION_ERROR_CODE, ERROR_MESSAGE, PATH } from 'src/constants';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import usePageTitle from 'src/hooks/usePageTitle';
import MyDrinkItem from './MyDrinkItem';
import { Container } from './styles';

const MyDrinksPage = () => {
  const history = useHistory();

  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  usePageTitle('선호도를 남긴 술');

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

  const observerTargetRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useInfinityScroll({ target: observerTargetRef, fetchNextPage, hasNextPage });

  const { matches } = window.matchMedia('screen and (max-width:380px)');

  const myDrinks = pages?.map((page) => page.data).flat();

  return (
    <>
      <NavigationHeader title="선호도를 남긴 술" />

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
