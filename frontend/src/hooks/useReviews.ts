import { useContext } from 'react';
import { useInfiniteQuery } from 'react-query';

import API from 'src/apis/requests';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import { ERROR_MESSAGE } from 'src/constants';

const useReviews = ({ drinkId }: { drinkId: string }) => {
  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const {
    data: reviewData,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery(
    'reviews',
    ({ pageParam = 1 }) =>
      API.getReview({ page: pageParam, params: new URLSearchParams({ drink: drinkId }) }),
    {
      getNextPageParam: ({ pageInfo }) => {
        return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
      },
      onError: (error: Request.Error) => {
        setSnackbarMessage?.({
          type: 'ERROR',
          message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT,
        });
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
