import { useEffect, useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { useQuery, useQueryClient } from 'react-query';

import UserContext from 'src/contexts/UserContext';

import API from 'src/apis/requests';

import NavigationHeader from 'src/components/Header/NavigationHeader';
import Grid from 'src/components/@shared/Grid/Grid';
import Preview from 'src/components/Preview/Preview';
import Profile from 'src/components/Profile/Profile';
import { HorizontalScroll } from 'src/components/Scroll/HorizontalScroll';
import Status from './Status';
import PersonalReviewItem from 'src/components/Item/PersonalReviewItem';

import MyDrinkItem from '../MyDrinksPage/MyDrinkItem';
import NoPreference from './NoPreference';
import NoReview from './NoReview';

import { PATH, VALUE } from 'src/constants';
import {
  SmileEmojiColorIcon,
  LoveEmojiColorIcon,
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
} from 'src/components/@Icons';
import PersonalDrinkItemSkeleton from 'src/components/Skeleton/PersonalDrinkItemSkeleton';
import PersonalReviewItemSkeleton from 'src/components/Skeleton/PersonalReviewItemSkeleton';
import { SurveyLink } from './styles';

const userProfileIcons = [
  SmileEmojiColorIcon,
  LoveEmojiColorIcon,
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
];

const defaultRequestData = { data: [], pageInfo: {} };

const MyPage = () => {
  const history = useHistory();

  const { userData, isLoggedIn, getUser } = useContext(UserContext);
  const UserProfileIcon = userProfileIcons[(userData?.id ?? 0) % VALUE.PROFILE_IMAGE_NUMBER];

  const queryClient = useQueryClient();

  const {
    data: {
      data: myDrinks = [],
      pageInfo: { totalSize: myDrinksCount } = { totalSize: 0 },
    } = defaultRequestData,
    isLoading: isMyDrinksLoading,
  } = useQuery(
    'my-drinks',
    () => API.getPersonalDrinks({ page: 1, size: VALUE.MYPAGE_DRINKS_DISPLAY_NUMBER }),
    {
      retry: 0,
    }
  );

  const {
    data: {
      data: myReviews,
      pageInfo: { totalSize: myReviewsCount } = { totalSize: 0 },
    } = defaultRequestData,
    isLoading: isMyReviewsLoading,
  } = useQuery(
    'my-reviews',
    () => API.getPersonalReviews({ page: 1, size: VALUE.MYPAGE_REVIEWS_DISPLAY_NUMBER }),
    {
      retry: 0,
    }
  );

  const isMyDrinksShowMoreEnabled = myDrinksCount > VALUE.MYPAGE_DRINKS_DISPLAY_NUMBER;
  const isMyReviewsShowMoreEnabled = myReviewsCount > VALUE.MYPAGE_REVIEWS_DISPLAY_NUMBER;

  useEffect(() => {
    const getFetch = async () => {
      await getUser();

      if (!queryClient.isFetching('user-info') && !isLoggedIn) {
        alert('마이페이지를 이용하시려면 로그인 해주세요');
        history.push(PATH.LOGIN);
      }
    };

    getFetch();
  }, [isLoggedIn]);

  return (
    <>
      <NavigationHeader title="내 정보" />
      <Profile ProfileIcon={UserProfileIcon} nickname={userData?.nickname} bio={userData?.bio} />
      <Status myDrinksCount={myDrinksCount} myReviewsCount={myReviewsCount} />

      <Preview
        title="선호도를 남긴 술"
        path={PATH.MY_DRINKS}
        isShowMoreEnabled={isMyDrinksShowMoreEnabled}
      >
        {isMyDrinksLoading ? (
          <Grid col={3} colGap="1rem">
            <PersonalDrinkItemSkeleton count={3} size="LARGE" />
          </Grid>
        ) : myDrinks?.length ? (
          <HorizontalScroll margin="0 -1.5rem" padding="0 1.5rem">
            <Grid col={VALUE.MYPAGE_DRINKS_DISPLAY_NUMBER} colGap="1rem">
              {myDrinks.map((myDrink: Drink.PersonalDrinkItem) => (
                <MyDrinkItem key={myDrink.id} size="LARGE" drink={myDrink} />
              ))}
            </Grid>
          </HorizontalScroll>
        ) : (
          <NoPreference />
        )}
      </Preview>

      <Preview
        title="내가 남긴 리뷰"
        path={PATH.MY_REVIEWS}
        isShowMoreEnabled={isMyReviewsShowMoreEnabled}
      >
        {isMyReviewsLoading ? (
          <Grid row={3} rowGap="1rem">
            <PersonalReviewItemSkeleton count={3} />
          </Grid>
        ) : myReviews?.length ? (
          <ul>
            {myReviews.map((myReview: Review.PersonalReviewItem) => (
              <PersonalReviewItem key={myReview.id} review={myReview} />
            ))}
          </ul>
        ) : (
          <NoReview myDrinks={myDrinks} />
        )}
      </Preview>

      <SurveyLink
        href="https://forms.gle/haUKZu3tNg1znfcZ7"
        target="_blank"
        rel="noopener noreferrer"
      >
        🚨 불편사항 신고 및 기타문의 작성하기
      </SurveyLink>
    </>
  );
};

export default MyPage;
