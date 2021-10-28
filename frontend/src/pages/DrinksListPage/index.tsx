import { useContext, useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router';

import API from 'src/apis/requests';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';
import { APPLICATION_ERROR_CODE, ERROR_MESSAGE, PATH } from 'src/constants';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import usePageTitle from 'src/hooks/usePageTitle';
import { Container, InfinityScrollPoll } from './styles';

const CATEGORY_NAME = {
  SOJU: '소주',
  BEER: '맥주',
  YANGJU: '양주',
  MAKGEOLLI: '막걸리',
  COCKTAIL: '칵테일',
  WINE: '와인',
  ETC: '기타 주류',
  ALL: '전체보기',
};

const DrinksListPage = () => {
  const history = useHistory();
  const infinityPollRef = useRef<HTMLDivElement>(null);

  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  usePageTitle('전체보기');

  const category =
    (new URLSearchParams(history.location.search).get('category') as keyof typeof CATEGORY_NAME) ??
    'ALL';
  const categoryName = CATEGORY_NAME[category];
  const params = new URLSearchParams({ category });

  const { data, fetchNextPage, hasNextPage, isFetching } = useInfiniteQuery(
    'drinks',
    ({ pageParam = 1 }) => API.getDrinks({ page: pageParam, params }),
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
  const drinks = data?.pages?.map((page) => page.data).flat() ?? [];
  const totalSize = data?.pages[0].pageInfo?.totalSize;

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useInfinityScroll({ target: infinityPollRef, fetchNextPage, hasNextPage });

  return (
    <Container>
      <NavigationHeader title={`${categoryName} (${totalSize ?? 0}개)`} />
      <List count={drinks?.length}>
        {drinks?.map((item: Drink.Item) => (
          <ListItem
            key={item?.id}
            itemId={item?.id}
            imageUrl={item?.imageResponse.small}
            title={item?.name}
            preferenceType={
              item?.preferenceRate ? 'MY' : item?.expectedPreference ? 'EXPECTED' : 'AVG'
            }
            preferenceRate={item?.preferenceRate || item?.expectedPreference || item?.preferenceAvg}
            description={`도수: ${item?.alcoholByVolume}%`}
          />
        ))}
        {isFetching && <ListItemSkeleton count={7} />}
      </List>
      <InfinityScrollPoll ref={infinityPollRef} />
    </Container>
  );
};

export default DrinksListPage;
