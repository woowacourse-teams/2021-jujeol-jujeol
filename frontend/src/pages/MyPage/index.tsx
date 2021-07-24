import { useHistory } from 'react-router-dom';
import ArrowButton from 'src/components/@shared/ArrowButton/ArrowButton';
import Grid from 'src/components/@shared/Grid/Grid';
import Preview from 'src/components/Preview/Preview';
import Profile from 'src/components/Profile/Profile';
import { Horizontal } from 'src/components/Scroll/Horizontal';
import { PATH } from 'src/constants';
import MyDrinkItem from '../MyDrinksPage/MyDrinkItem';
import MyReviewItem from '../MyReviewsPage/MyReviewItem';

import { Header, Statistics } from './styles';

const MyPage = () => {
  const history = useHistory();

  return (
    <>
      <Header>
        <ArrowButton size="0.7rem" borderSize="2px" dir="LEFT" onClick={() => history.goBack()} />
        <h2>내정보</h2>
      </Header>

      <Profile src="http://placehold.it/72x72" nickname="청바지_122" bio="청춘은 바로 지금" />

      <Statistics>
        <li>
          <p>43개</p>
          <p>내가 마신 술</p>
        </li>
        <li>
          <p>15개</p>
          <p>내가 남긴 리뷰</p>
        </li>
      </Statistics>

      <Preview title="내가 마신 술" path={PATH.MY_DRINKS}>
        <Horizontal margin="0 -1.5rem" padding="0 1.5rem">
          <Grid col={7} colGap="1rem">
            {Array.from({ length: 7 }).map((_, index) => (
              <MyDrinkItem key={index} size="LARGE" />
            ))}
          </Grid>
        </Horizontal>
      </Preview>

      <Preview title="내가 남긴 리뷰" path={PATH.MY_REVIEWS}>
        <ul>
          {Array.from({ length: 3 }).map((_, index) => (
            <MyReviewItem key={index} />
          ))}
        </ul>
      </Preview>
    </>
  );
};

export default MyPage;
