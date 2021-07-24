import ReviewCard from '../Card/ReviewCard';
import ReviewCreateForm from './ReviewCreateForm';
import { Wrapper, ReviewList } from './Review.styles';

const Review = ({ reviews, totalSize }: Review.ReviewList & { totalSize: number }) => {
  return (
    <Wrapper>
      <h2>리뷰 {totalSize}개</h2>
      <ReviewCreateForm />

      <ReviewList>
        {reviews?.map((review) => {
          return (
            <li key={review.id}>
              <ReviewCard review={review} />
            </li>
          );
        })}
      </ReviewList>
    </Wrapper>
  );
};

export default Review;
