import { useContext, useEffect, useRef } from 'react';
import { useInfiniteQuery, useMutation, useQueryClient } from 'react-query';
import { useHistory } from 'react-router';
import { Link } from 'react-router-dom';

import API from 'src/apis/requests';
import { DizzyEmojiColorIcon } from 'src/components/@Icons';
import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import Grid from 'src/components/@shared/Grid/Grid';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import { ERROR_MESSAGE, PATH } from 'src/constants';
import QUERY_KEY from 'src/constants/queryKey';
import UserContext from 'src/contexts/UserContext';
import usePageTitle from 'src/hooks/usePageTitle';
import MemoizedPreferenceItem from './PreferenceItem';
import { AlertWrapper, Container, InfinityScrollPoll, NoDrink, Notification } from './styles';

const PreferencePage = () => {
  usePageTitle('선호도 평가');

  const history = useHistory();

  const isLoggedIn = useContext(UserContext)?.isLoggedIn;
  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};
  const infinityPollRef = useRef<HTMLDivElement>(null);

  const queryClient = useQueryClient();

  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery(
    QUERY_KEY.DRINK_LIST_SORTED_BY_PREFERENCE,
    ({ pageParam = 1 }) =>
      API.getDrinks({
        page: pageParam,
        params: new URLSearchParams('size=25'),
      }),
    {
      getNextPageParam: ({ pageInfo }) => {
        return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
      },
      onError: (error: Request.Error) => {
        setSnackbarMessage?.({
          type: 'ERROR',
          message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT,
        });
      },
    }
  );
  const drinks = data?.pages?.map((page) => page.data).flat() ?? [];

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting && hasNextPage) {
        fetchNextPage();
      }
    });
  });

  const { mutate: onUpdatePreference } = useMutation(
    ({ id, preferenceRate }: { id: number; preferenceRate: number }) => {
      if (preferenceRate === 0) {
        return API.deletePreference<number>(id);
      }

      return API.postPreference<number, { preferenceRate: number }>(id, {
        preferenceRate,
      });
    },
    {
      onError: (error: Request.Error) => {
        setSnackbarMessage?.({
          type: 'ERROR',
          message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT,
        });

        queryClient.removeQueries(QUERY_KEY.DRINK_LIST_SORTED_BY_PREFERENCE);
      },
    }
  );

  const onMoveToDrinkDetail = (id: number) => () => {
    history.push(`${PATH.DRINKS}/${id}`);
  };

  useEffect(() => {
    if (infinityPollRef.current) {
      observer.observe(infinityPollRef.current);
    }

    return () => {
      if (infinityPollRef.current) {
        observer.unobserve(infinityPollRef.current);
      }
    };
  }, [infinityPollRef.current]);

  useEffect(() => {
    if (!drinks.filter(({ preferenceRate }) => !preferenceRate).length && hasNextPage) {
      fetchNextPage();
    }
  }, [drinks]);

  return (
    <>
      <NavigationHeader title="선호도 평가" />
      <Container isLoggedIn={isLoggedIn}>
        <Notification>
          <p>술에 대한 선호도를 많이 남길수록, 추천 정확도가 높아져요</p>
        </Notification>
        <div>
          <Grid rowGap="1.5rem">
            {drinks
              .filter(({ preferenceRate }) => !preferenceRate)
              .map(({ id, name, imageResponse, preferenceRate }) => (
                <li key={id}>
                  <MemoizedPreferenceItem
                    name={name}
                    id={id}
                    labelText={`${name} 선호도 입력`}
                    imageUrl={imageResponse.small}
                    initialValue={preferenceRate}
                    onUpdatePreference={onUpdatePreference}
                    onClickImage={onMoveToDrinkDetail(id)}
                  />
                </li>
              ))}
          </Grid>
          {isFetching &&
            Array.from({ length: 7 }).map((_, index) => (
              <FlexBox key={index} margin="0 0 2rem">
                <Skeleton type="SQUARE" size="X_SMALL" width="5.5rem" margin="0 1rem 0 0" />
                <Skeleton type="SQUARE" size="X_SMALL" width="100%" />
              </FlexBox>
            ))}
          {!drinks.filter(({ preferenceRate }) => !preferenceRate).length && !hasNextPage && (
            <NoDrink>
              <DizzyEmojiColorIcon />
              <p>주절주절에 있는 모든 술을 드셨네요!</p>
              <p>회원님을 이 구역의 술쟁이로 인정합니다!</p>
            </NoDrink>
          )}
        </div>
        <InfinityScrollPoll ref={infinityPollRef} />
        {!isLoggedIn && (
          <AlertWrapper>
            <p>선호도를 남기시려면 로그인 해주세요:)</p>
            <Link to={PATH.LOGIN}>{`로그인하러가기 >`}</Link>
          </AlertWrapper>
        )}
      </Container>
    </>
  );
};

export default PreferencePage;
