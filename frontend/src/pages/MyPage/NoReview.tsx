import { useHistory } from 'react-router-dom';
import Card from 'src/components/@shared/Card/Card';
import Grid from 'src/components/@shared/Grid/Grid';
import { StarIcon } from 'src/components/@Icons';
import { Img } from 'src/components/@shared/Image/Image';
import { COLOR, PATH } from 'src/constants';
import { Description, Header } from './NoReview.styles';

const NoReview = ({ myDrinks }: { myDrinks: Drink.PersonalDrinkItem[] }) => {
  const history = useHistory();

  const onMoveToDrinkDetail =
    ({ id }: Pick<Drink.PersonalDrinkItem, 'id'>) =>
    () => {
      history.push(`${PATH.DRINKS}/${id}`);
    };

  return (
    <>
      <Header>
        <p>남긴 리뷰가 없습니다.</p>
        <p>리뷰를 남겨보시는건 어떠세요?</p>
      </Header>
      <Grid rowGap="1rem">
        {myDrinks?.slice(0, 3).map(({ id, name, imageUrl, preferenceRate }) => (
          <li key={id}>
            <Card
              padding="1rem"
              backgroundColor={COLOR.GRAY_100}
              color={COLOR.BLACK}
              flexDirection="row"
            >
              <Img src={imageUrl} alt={name} size="X_SMALL" shape="ROUND_SQUARE" />
              <Description>
                <p>{name}</p>
                <div>
                  <StarIcon width="0.8rem" color={COLOR.YELLOW_300} />
                  <span>{preferenceRate.toFixed(1)}</span>
                </div>
                <button type="button" onClick={onMoveToDrinkDetail({ id })}>
                  리뷰하기
                </button>
              </Description>
            </Card>
          </li>
        ))}
      </Grid>
    </>
  );
};

export default NoReview;
