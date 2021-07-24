import PersonalReviewCard from 'src/components/Card/PersonalReviewCard';
import { Item } from './MyReviewItem.styles';

interface Props {
  review: MyReview.MyReviewItem;
}

const MyReviewItem = ({ review }: Props) => {
  return (
    <Item>
      <PersonalReviewCard review={review} />
    </Item>
  );
};

export default MyReviewItem;
