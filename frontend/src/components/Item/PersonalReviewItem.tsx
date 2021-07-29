import PersonalReviewCard from 'src/components/Card/PersonalReviewCard';
import { Item } from './PersonalReviewItem.styles';

interface Props {
  review: PersonalReview.PersonalReviewItem;
}

const MyReviewItem = ({ review }: Props) => {
  return (
    <Item>
      <PersonalReviewCard review={review} />
    </Item>
  );
};

export default MyReviewItem;
