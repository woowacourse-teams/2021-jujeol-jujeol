import { useState, useEffect, useContext } from 'react';
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

import { PATH } from 'src/constants';
import {
  SmileEmojiColorIcon,
  LoveEmojiColorIcon,
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
} from 'src/components/@shared/Icons';

const userProfileIcons = [
  SmileEmojiColorIcon,
  LoveEmojiColorIcon,
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
];

const MyPage = () => {
  const history = useHistory();

  const { userData, isLoggedIn, getUser } = useContext(UserContext);

  const [myDrinks, setMyDrinks] = useState([]);
  const [myReviews, setMyReviews] = useState([]);

  const queryClient = useQueryClient();

  const isMyDrinksShowMoreEnabled = myDrinks.length > 2;
  const isMyReviewsShowMoreEnabled = myReviews.length > 3;
  const UserProfileIcon = userProfileIcons[(userData?.id ?? 0) % 4];

  const { data: myDrinksData } = useQuery(
    'my-drinks',
    () => API.getPersonalDrinks({ page: 1, size: 7 }),
    {
      retry: 0,
      onSuccess: ({ data }) => {
        setMyDrinks(data);
      },
    }
  );

  const { data: myReviewsData } = useQuery(
    'my-reviews',
    () => API.getPersonalReviews({ page: 1, size: 4 }),
    {
      retry: 0,
      onSuccess: ({ data }) => {
        setMyReviews(data);
      },
    }
  );

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
      <Status
        myDrinksCount={myDrinksData?.pageInfo?.totalSize}
        myReviewsCount={myReviewsData?.pageInfo?.totalSize}
      />

      <Preview
        title="선호도를 남긴 술"
        path={PATH.MY_DRINKS}
        isShowMoreEnabled={isMyDrinksShowMoreEnabled}
      >
        {myDrinks?.length ? (
          <HorizontalScroll margin="0 -1.5rem" padding="0 1.5rem">
            <Grid col={7} colGap="1rem">
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
        {myReviews?.length ? (
          <ul>
            {myReviews.slice(0, 3).map((myReview: Review.PersonalReviewItem) => (
              <PersonalReviewItem key={myReview.id} review={myReview} />
            ))}
          </ul>
        ) : (
          <NoReview myDrinks={myDrinks} />
        )}
      </Preview>
    </>
  );
};

export default MyPage;
