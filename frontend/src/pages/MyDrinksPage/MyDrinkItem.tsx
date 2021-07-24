import StarIcon from 'src/components/Icons/star';
import VerticalItem from 'src/components/Item/VerticalItem';
import { COLOR } from 'src/constants';
import { Description } from './MyDrinkItem.styles';

const MyDrinkItem = () => {
  return (
    <VerticalItem
      src="http://placehold.it/120x120"
      alt="호감도를 입력한 술"
      shape="ROUND_SQUARE"
      size="LARGE"
      title="KGB 라임"
    >
      <Description>
        <StarIcon width="0.8rem" color={COLOR.YELLOW_300} />
        <span>3.5</span>
      </Description>
    </VerticalItem>
  );
};

export default MyDrinkItem;
