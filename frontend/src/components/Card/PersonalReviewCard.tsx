import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import { Img } from '../@shared/Image/Image';
import TextReview from '../Review/TextReview';

interface Props {
  review: MyReview.MyReviewItem;
}

const PersonalReviewCard = ({ review }: Props) => {
  const { drink } = review;

  return (
    <Card
      width="100%"
      backgroundColor="transparent"
      padding="0.6rem 1.3rem 0.6rem 0.6rem"
      border={`1px solid ${COLOR.PURPLE_100}`}
    >
      <Img src={drink.imageUrl} alt={drink.name} shape="ROUND_SQUARE" size="SMALL" />
      <TextReview review={review} />
    </Card>
  );
};

export default PersonalReviewCard;
