import { useHistory } from 'react-router-dom';
import ArrowButton from 'src/components/@shared/ArrowButton/ArrowButton';
import Grid from 'src/components/@shared/Grid/Grid';
import { Header } from '../MyPage/styles';
import MyDrinkItem from './MyDrinkItem';

const MyDrinks = () => {
  const history = useHistory();
  const { matches } = window.matchMedia('screen and (max-width:380px)');

  return (
    <>
      <Header>
        <ArrowButton size="0.7rem" borderSize="2px" dir="LEFT" onClick={() => history.goBack()} />
        <h2>내가 마신 술</h2>
      </Header>

      <section style={{ padding: '1rem' }}>
        {matches ? (
          <Grid col={2} rowGap="1.5rem">
            {Array.from({ length: 15 }).map((_, index) => (
              <MyDrinkItem key={index} size="X_LARGE" />
            ))}
          </Grid>
        ) : (
          <Grid col={3} rowGap="1.5rem">
            {Array.from({ length: 15 }).map((_, index) => (
              <MyDrinkItem key={index} size="LARGE" />
            ))}
          </Grid>
        )}
      </section>
    </>
  );
};

export default MyDrinks;
