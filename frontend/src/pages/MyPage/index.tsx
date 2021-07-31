import { useState, useEffect, useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { useQuery } from 'react-query';

import UserContext from 'src/contexts/UserContext';

import API from 'src/apis/requests';

import Grid from 'src/components/@shared/Grid/Grid';
import Preview from 'src/components/Preview/Preview';
import Profile from 'src/components/Profile/Profile';
import { HorizontalScroll } from 'src/components/Scroll/HorizontalScroll';
import Status from './Status';
import Arrow from 'src/components/@shared/Arrow/Arrow';
import PersonalReviewItem from 'src/components/Item/PersonalReviewItem';

import MyDrinkItem from '../MyDrinksPage/MyDrinkItem';

import { PATH } from 'src/constants';
import { Header } from './styles';

const MyPage = () => {
  const history = useHistory();

  const { userData, isLoggedIn, getUser } = useContext(UserContext);

  const [myDrinks, setMyDrinks] = useState([]);
  const [myReviews, setMyReviews] = useState([]);

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
    () => API.getPersonalReviews({ page: 1, size: 3 }),
    {
      retry: 0,
      onSuccess: ({ data }) => {
        setMyReviews(data);
      },
    }
  );

  useEffect(() => {
    getUser();

    if (!isLoggedIn) {
      history.push(PATH.LOGIN);
    }
  }, []);

  const onMoveToPrevPage = () => history.goBack();
  return (
    <>
      <Header>
        <button type="button" onClick={onMoveToPrevPage}>
          <Arrow size="0.7rem" borderWidth="2px" dir="LEFT" />
        </button>
        <h2>내정보</h2>
      </Header>
      <Profile src="https://fakeimg.pl/72x72" nickname={userData?.nickname} bio={userData?.bio} />
      <Status
        myDrinksCount={myDrinksData?.pageInfo?.totalSize}
        myReviewsCount={myReviewsData?.pageInfo?.totalSize}
      />

      <Preview title="내가 마신 술" path={PATH.MY_DRINKS}>
        <HorizontalScroll margin="0 -1.5rem" padding="0 1.5rem">
          <Grid col={7} colGap="1rem">
            {myDrinks?.map((myDrink: MyDrink.MyDrinkItem) => (
              <MyDrinkItem key={myDrink.id} size="LARGE" drink={myDrink} />
            ))}
          </Grid>
        </HorizontalScroll>
      </Preview>

      <Preview title="내가 남긴 리뷰" path={PATH.MY_REVIEWS}>
        <ul>
          {myReviews?.map((myReview: PersonalReview.PersonalReviewItem) => (
            <PersonalReviewItem key={myReview.id} review={myReview} />
          ))}
        </ul>
      </Preview>
    </>
  );
};

export default MyPage;
