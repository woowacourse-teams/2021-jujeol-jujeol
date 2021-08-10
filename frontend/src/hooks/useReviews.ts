import { useInfiniteQuery } from 'react-query';
import API from 'src/apis/requests';

const useReviews = ({ drinkId }: { drinkId: string }) => {
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

  return { reviews, totalSize, fetchNextPage, hasNextPage };
};

export default useReviews;
