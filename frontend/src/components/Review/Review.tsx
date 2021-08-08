import { MouseEventHandler, useRef } from 'react';

import useReviews from 'src/hooks/useReviews';

import ReviewCreateForm from './ReviewCreateForm';
import ReviewCard from '../Card/ReviewCard';
import InfinityScrollPoll from '../@shared/InfinityScrollPoll/InfinityScrollPoll';
import NoReviews from './NoReviews';
import { Wrapper, ReviewList } from './Review.styles';
import DisableWriteReview from './DisableWriteReview';

interface Props {
  drinkId: string;
  drinkName: string;
  preferenceRate: number;
  onMoveToPreferenceSection: MouseEventHandler<HTMLButtonElement>;
}

const Review = ({ drinkId, drinkName, preferenceRate, onMoveToPreferenceSection }: Props) => {
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const { totalSize, reviews } = useReviews({ drinkId, observerTargetRef });

  return (
    <Wrapper>
      <h2>리뷰 {totalSize}개</h2>
      {preferenceRate ? (
        <ReviewCreateForm />
      ) : (
        <DisableWriteReview onClick={onMoveToPreferenceSection} />
      )}

      {totalSize === 0 ? (
        <NoReviews drinkName={drinkName} />
      ) : (
        <>
          <ReviewList>
            {reviews?.map((review) => {
              return (
                <li key={review.id}>
                  <ReviewCard review={review} />
                </li>
              );
            })}
          </ReviewList>
          <InfinityScrollPoll ref={observerTargetRef} />
        </>
      )}
    </Wrapper>
  );
};

export default Review;
