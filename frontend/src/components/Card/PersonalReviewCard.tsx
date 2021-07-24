import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import { Img } from '../@shared/Image/Image';
import TextReview from '../Review/TextReview';

const PersonalReviewCard = () => {
  return (
    <Card
      width="100%"
      backgroundColor="transparent"
      padding="0.6rem 1.3rem 0.6rem 0.6rem"
      border={`1px solid ${COLOR.PURPLE_100}`}
    >
      <Img
        src="http://placehold.it/100x100"
        alt="리뷰를 입력한 술"
        shape="ROUND_SQUARE"
        size="SMALL"
      />
      <TextReview />
    </Card>
  );
};

export default PersonalReviewCard;
