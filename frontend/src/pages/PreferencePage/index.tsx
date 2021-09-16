import { useContext, useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { Link } from 'react-router-dom';
import API from 'src/apis/requests';
import Grid from 'src/components/@shared/Grid/Grid';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import { PATH } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import { InfinityScrollPoll } from '../ViewAllPage/ViewAllPage.styles';
import MemoizedPreferenceItem from './PreferenceItem';
import { Container, AlertWrapper, DrinksList } from './styles';

const PreferencePage = () => {
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;
  const infinityPollRef = useRef<HTMLDivElement>(null);

  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery(
    'preference-drinks',
    ({ pageParam = 1 }) =>
      API.getDrinks({ page: pageParam, params: new URLSearchParams('sortBy=expectPreference') }),
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
  }, [infinityPollRef.current]);

  return (
    <Container>
      <NavigationHeader title="선호도 평가" />
      <div>
        {/* 상황에 따라 표시되는 문구 변경 */}
        <p>3개 이상의 술을 평가해야 추천을 해드릴 수 있어요</p>
        <br />
      </div>
      <DrinksList>
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
      </DrinksList>
      <InfinityScrollPoll ref={infinityPollRef} />
      {!isLoggedIn && (
        <AlertWrapper>
          <p>선호도를 남기시려면 로그인 해주세요:)</p>
          <Link to={PATH.LOGIN}>{`로그인하러가기 >`}</Link>
        </AlertWrapper>
      )}
    </Container>
  );
};

export default PreferencePage;
