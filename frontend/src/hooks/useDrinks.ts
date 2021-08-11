import { useInfiniteQuery } from 'react-query';
import API from 'src/apis/requests';

const useDrinks = () => {
  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery(
    'drinks',
    ({ pageParam = 1 }) => API.getDrinks({ page: pageParam }),
    {
      getNextPageParam: ({ pageInfo }) => {
        return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
      },
    }
  );

  const drinks = data?.pages?.map((page) => page.data).flat() ?? [];

  const getNextDrinks = () => fetchNextPage;

  return { drinks, getNextDrinks, hasNextDrinks: hasNextPage };
};

export default useDrinks;
