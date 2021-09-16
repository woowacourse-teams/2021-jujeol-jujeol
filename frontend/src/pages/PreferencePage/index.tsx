import { useContext, useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { Link } from 'react-router-dom';
import API from 'src/apis/requests';
import Grid from 'src/components/@shared/Grid/Grid';
import { DizzyEmojiColorIcon } from 'src/components/@shared/Icons';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import { PATH } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import { InfinityScrollPoll } from '../ViewAllPage/ViewAllPage.styles';
import MemoizedPreferenceItem from './PreferenceItem';
import { Container, AlertWrapper, NoDrink, Notification } from './styles';

const PreferencePage = () => {
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;
  const infinityPollRef = useRef<HTMLDivElement>(null);

  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery(
    'preference-drinks',
    ({ pageParam = 1 }) =>
      API.getDrinks({
        page: pageParam,
        params: new URLSearchParams('sortBy=preferenceAvg&size=10'),
      }),
    {
      getNextPageParam: ({ pageInfo }) => {
        return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
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

  const onUpdatePreference = (id: number) => (value: number) => {
    if (isLoggedIn) {
      if (value === 0) {
        API.deletePreference<number>(id);
        return;
      }

      API.postPreference<number, { preferenceRate: number }>(id, {
        preferenceRate: value,
      });
    }
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
  }, [infinityPollRef.current, drinks.length]);

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
                    key={id}
                    id={id}
                    name={name}
                    imageUrl={imageResponse.small}
                    initialValue={preferenceRate}
                    onUpdatePreference={onUpdatePreference(id)}
                  />
                </li>
              ))}
          </Grid>
          {!drinks.filter(({ preferenceRate }) => !preferenceRate).length && (
            <NoDrink>
              <DizzyEmojiColorIcon />
              <h2>주절주절에 있는 모든 술을 드셨네요!</h2>
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
