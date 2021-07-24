import { useEffect, useState, useContext } from 'react';
import { useHistory } from 'react-router-dom';
import { useQuery } from 'react-query';

import API from 'src/apis/requests';
import ArrowButton from 'src/components/@shared/ArrowButton/ArrowButton';
import Grid from 'src/components/@shared/Grid/Grid';
import Preview from 'src/components/Preview/Preview';
import Profile from 'src/components/Profile/Profile';
import { Horizontal } from 'src/components/Scroll/Horizontal';
import { PATH } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import MyDrinkItem from '../MyDrinksPage/MyDrinkItem';
import MyReviewItem from '../MyReviewsPage/MyReviewItem';

import { Header, Statistics } from './styles';

const MyPage = () => {
  const history = useHistory();

  const { userData, isLoggedIn, getUser } = useContext(UserContext);

  const [myDrinks, setMyDrinks] = useState([]);
  const [myReviews, setMyReviews] = useState([]);
  const [totalMyDrinks, setTotalMyDrinks] = useState(0);
  const [totalReviews, setTotalReviews] = useState(0);

  const myDrinksQuery = useQuery('my-drinks', API.getMyDrinks, {
    retry: 0,
    onSuccess: ({ data, pageInfo }) => {
      console.log(data);
      setMyDrinks(data);
      setTotalMyDrinks(pageInfo.totalSize);
    },
  });

  const myReviewsQuery = useQuery('my-reviews', API.getMyReviews, {
    retry: 0,
    onSuccess: ({ data, pageInfo }) => {
      setMyReviews(data);
      setTotalReviews(pageInfo.totalSize);
    },
  });

  useEffect(() => {
    getUser();

    if (!isLoggedIn) {
      history.push(PATH.LOGIN);
    }
  }, []);

  return (
    <>
      <Header>
        <ArrowButton size="0.7rem" borderSize="2px" dir="LEFT" onClick={() => history.goBack()} />
        <h2>내정보</h2>
      </Header>

      <Profile src="http://placehold.it/72x72" nickname={userData?.nickname} bio={userData?.bio} />

      <Statistics>
        <li>
          <p>{totalMyDrinks}개</p>
          <p>내가 마신 술</p>
        </li>
        <li>
          <p>{totalReviews}개</p>
          <p>내가 남긴 리뷰</p>
        </li>
      </Statistics>

      <Preview title="내가 마신 술" path={PATH.MY_DRINKS}>
        <Horizontal margin="0 -1.5rem" padding="0 1.5rem">
          <Grid col={7} colGap="1rem">
            {myDrinks?.map((myDrink: MyDrink.MyDrinkItem) => (
              <MyDrinkItem key={myDrink.id} size="LARGE" drink={myDrink} />
            ))}
          </Grid>
        </Horizontal>
      </Preview>

      <Preview title="내가 남긴 리뷰" path={PATH.MY_REVIEWS}>
        <ul>
          {myReviews?.map((myReview: MyReview.MyReviewItem) => (
            <MyReviewItem key={myReview.id} review={myReview} />
          ))}
        </ul>
      </Preview>
    </>
  );
};

export default MyPage;
