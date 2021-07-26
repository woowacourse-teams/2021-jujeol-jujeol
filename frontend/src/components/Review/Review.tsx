import { useInfiniteQuery } from 'react-query';
import { useEffect, useRef } from 'react';
import API from 'src/apis/requests';
import ReviewCreateForm from './ReviewCreateForm';
import ReviewCard from '../Card/ReviewCard';
import { Wrapper, ReviewList, InfinityScrollPoll } from './Review.styles';

const Review = ({ drinkId }: { drinkId: string }) => {
  const infinityPollRef = useRef<HTMLDivElement>(null);

  const { data: reviewData, fetchNextPage } = useInfiniteQuery<{
    data: Review.ReviewItem[];
    pageInfo: { currentPage: number; lastPage: number; countPerPage: number; totalSize: number };
  }>('reviews', ({ pageParam = 1 }) => API.getReview<string>({ id: drinkId, page: pageParam }), {
    getNextPageParam: ({ pageInfo }) => {
      return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
    },
  });
  const reviews = reviewData?.pages?.map((page) => page.data).flat() ?? [];
  const [
    {
      pageInfo: { totalSize },
    },
  ] = reviewData?.pages ?? [{ pageInfo: { totalSize: 0 } }];

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        fetchNextPage();
      }
    });
  });

  useEffect(() => {
    if (infinityPollRef.current) {
      observer.observe(infinityPollRef.current);
    }
  }, [infinityPollRef.current]);

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

      <InfinityScrollPoll ref={infinityPollRef} />
    </Wrapper>
  );
};

export default Review;
