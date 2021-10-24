import { Link } from 'react-router-dom';

import PersonalReviewCard from 'src/components/Card/PersonalReviewCard';
import { PATH } from 'src/constants';
import { Item } from './PersonalReviewItem.styles';

interface Props {
  review: Review.PersonalReviewItem;
}

const MyReviewItem = ({ review }: Props) => {
  return (
    <Item>
      <Link to={`${PATH.DRINKS}/${review.drink.drinkId}`}>
        <PersonalReviewCard review={review} />
      </Link>
    </Item>
  );
};

export default MyReviewItem;
