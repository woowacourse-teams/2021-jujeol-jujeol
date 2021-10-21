import { MouseEventHandler, useRef } from 'react';

import useReviews from 'src/hooks/useReviews';
import useInfinityScroll from 'src/hooks/useInfinityScroll';

import ReviewCreateForm from './ReviewCreateForm';
import ReviewCard from '../Card/ReviewCard';
import InfinityScrollPoll from '../@shared/InfinityScrollPoll/InfinityScrollPoll';
import NoReviews from './NoReviews';
import DisableWriteReview from './DisableWriteReview';
import { Wrapper, ReviewList } from './Review.styles';
import Heading from '../@shared/Heading/Heading';
import { css } from '@emotion/react';

interface Props {
  drinkId: string;
  drinkName: string;
  preferenceRate: number;
  onNoticeToInputPreference: MouseEventHandler<HTMLButtonElement>;
}

const Review = ({ drinkId, drinkName, preferenceRate, onNoticeToInputPreference }: Props) => {
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const { reviews, totalSize, fetchNextPage, hasNextPage } = useReviews({ drinkId });
  useInfinityScroll({ target: observerTargetRef, fetchNextPage, hasNextPage });

  return (
    <Wrapper>
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
