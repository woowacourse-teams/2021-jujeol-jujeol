import { useContext } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router-dom';

import API from 'src/apis/requests';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import { APPLICATION_ERROR_CODE, ERROR_MESSAGE, PATH } from 'src/constants';
import QUERY_KEY from 'src/constants/queryKey';

const useReviews = ({ drinkId }: { drinkId: string }) => {
  const history = useHistory();

  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const {
    data: reviewData,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery(
    QUERY_KEY.REVIEW_LIST,
    ({ pageParam = 1 }) =>
      API.getReview({ page: pageParam, params: new URLSearchParams({ drink: drinkId }) }),
    {
      getNextPageParam: ({ pageInfo }) => {
        return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
      },
      onError: (error: Request.Error) => {
        if (
          error.code === APPLICATION_ERROR_CODE.NETWORK_ERROR ||
          error.code === APPLICATION_ERROR_CODE.INTERNAL_SERVER_ERROR
        ) {
          history.push({
            pathname: PATH.ERROR_PAGE,
            state: { code: error.code },
          });
        }

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
