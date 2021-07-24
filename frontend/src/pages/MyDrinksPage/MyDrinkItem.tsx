import { useEffect } from 'react';
import { ImageSizeType } from 'src/components/@shared/Image/Image';
import StarIcon from 'src/components/Icons/star';
import VerticalItem from 'src/components/Item/VerticalItem';
import { COLOR } from 'src/constants';
import { Description } from './MyDrinkItem.styles';

interface Props {
  size: ImageSizeType;
  drink: MyDrink.MyDrinkItem;
}

const MyDrinkItem = ({ size, drink }: Props) => {
  const { imageUrl, name, preferenceRate } = drink;

  return (
    <VerticalItem src={imageUrl} alt={name} shape="ROUND_SQUARE" size={size} title={name}>
      <Description>
        <StarIcon width="0.8rem" color={COLOR.YELLOW_300} />
        <span>{preferenceRate}</span>
      </Description>
    </VerticalItem>
  );
};

export default MyDrinkItem;
