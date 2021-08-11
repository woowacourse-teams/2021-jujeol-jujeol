import { useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import API from 'src/apis/requests';
import ReviewCreateForm from './ReviewCreateForm';
import ReviewCard from '../Card/ReviewCard';
import { Wrapper, ReviewList } from './Review.styles';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import InfinityScrollPoll from '../@shared/InfinityScrollPoll/InfinityScrollPoll';
import ReviewItemSkeleton from '../@shared/Skeleton/ReviewItemSkeleton';

const Review = ({ drinkId }: { drinkId: string }) => {
  const observerTargetRef = useRef<HTMLDivElement>(null);

  const {
    data: reviewData,
    fetchNextPage,
    hasNextPage,
    isFetching,
  } = useInfiniteQuery(
    'reviews',
    ({ pageParam = 1 }) => API.getReview<string>({ id: drinkId, page: pageParam }),
    {
      getNextPageParam: ({ pageInfo }) => {
        return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
      },
    }
  );
  const reviews = reviewData?.pages?.map((page) => page.data).flat() ?? [];
  const [
    {
      pageInfo: { totalSize },
    },
  ] = reviewData?.pages ?? [{ pageInfo: { totalSize: 0 } }];

  useInfinityScroll({ target: observerTargetRef, fetchNextPage, hasNextPage });

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
        {isFetching && <ReviewItemSkeleton count={3} />}
      </ReviewList>

      <InfinityScrollPoll ref={observerTargetRef} />
    </Wrapper>
  );
};

export default Review;
