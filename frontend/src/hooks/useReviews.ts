import { RefObject } from 'react';
import { useInfiniteQuery } from 'react-query';
import API from 'src/apis/requests';
import useInfinityScroll from './useInfinityScroll';

const useReviews = ({
  drinkId,
  observerTargetRef,
}: {
  drinkId: string;
  observerTargetRef: RefObject<HTMLDivElement>;
}) => {
  const {
    data: reviewData,
    fetchNextPage,
    hasNextPage,
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

  return { reviews, totalSize };
};

export default useReviews;
