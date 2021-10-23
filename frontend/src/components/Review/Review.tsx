import { MouseEventHandler, useRef } from 'react';
import { css } from '@emotion/react';

import useInfinityScroll from 'src/hooks/useInfinityScroll';
import useReviews from 'src/hooks/useReviews';
import Heading from '../@shared/Heading/Heading';
import InfinityScrollPoll from '../@shared/InfinityScrollPoll/InfinityScrollPoll';
import ReviewCard from '../Card/ReviewCard';
import DisableWriteReview from './DisableWriteReview';
import NoReviews from './NoReviews';
import { ReviewList, Wrapper } from './Review.styles';
import ReviewCreateForm from './ReviewCreateForm';

interface Props {
  id: string;
  drinkId: string;
  drinkName: string;
  preferenceRate: number;
  onNoticeToInputPreference: MouseEventHandler<HTMLButtonElement>;
}

const Review = ({ id, drinkId, drinkName, preferenceRate, onNoticeToInputPreference }: Props) => {
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const { reviews, totalSize, fetchNextPage, hasNextPage } = useReviews({ drinkId });
  useInfinityScroll({ target: observerTargetRef, fetchNextPage, hasNextPage });

  return (
    <Wrapper id={id}>
      <Heading.level3
        css={css`
          margin-bottom: 0.5rem;
          padding-left: 0.75rem;
        `}
      >
        리뷰 {totalSize}개
      </Heading.level3>
      {preferenceRate ? (
        <ReviewCreateForm />
      ) : (
        <DisableWriteReview onNoticeToInputPreference={onNoticeToInputPreference} />
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
