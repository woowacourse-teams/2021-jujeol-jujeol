import { Link } from 'react-router-dom';

import StarIcon from 'src/components/@Icons/StarIcon';
import { ImageSizeType, Img } from 'src/components/@shared/Image/Image';
import { COLOR, PATH } from 'src/constants';
import { Container, Description } from './MyDrinkItem.styles';

interface Props {
  size: ImageSizeType;
  drink: Drink.PersonalDrinkItem;
}

const MyDrinkItem = ({ size, drink }: Props) => {
  const { imageUrl, name, preferenceRate } = drink;

  return (
    <Container>
      <Link to={`${PATH.DRINKS}/${drink.id}`}>
        <Img src={imageUrl} alt={name} shape="ROUND_SQUARE" size={size} />
        <p>{name}</p>
        <Description>
          <StarIcon width="0.8rem" color={COLOR.YELLOW_300} />
          <span>{preferenceRate.toFixed(1)}</span>
        </Description>
      </Link>
    </Container>
  );
};

export default MyDrinkItem;
