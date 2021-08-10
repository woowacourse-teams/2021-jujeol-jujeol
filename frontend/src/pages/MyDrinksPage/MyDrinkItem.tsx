import { useHistory } from 'react-router-dom';
import { ImageSizeType, Img } from 'src/components/@shared/Image/Image';
import StarIcon from 'src/components/@shared/Icons/StarIcon';
import { COLOR, PATH } from 'src/constants';
import { Container, Description } from './MyDrinkItem.styles';

interface Props {
  size: ImageSizeType;
  drink: Drink.PersonalDrinkItem;
}

const MyDrinkItem = ({ size, drink }: Props) => {
  const { imageUrl, name, preferenceRate } = drink;

  const history = useHistory();

  const onMoveToDrinkDetail = () => history.push(`${PATH.DRINKS}/${drink.id}`);

  return (
    <Container onClick={onMoveToDrinkDetail}>
      <Img src={imageUrl} alt={name} shape="ROUND_SQUARE" size={size} />
      <p>{name}</p>
      <Description>
        <StarIcon width="0.8rem" color={COLOR.YELLOW_300} />
        <span>{preferenceRate.toFixed(1)}</span>
      </Description>
    </Container>
  );
};

export default MyDrinkItem;
