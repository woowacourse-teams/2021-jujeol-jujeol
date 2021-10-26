import { useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router';

import API from 'src/apis/requests';
import NavigationHeader from 'src/components/Header/NavigationHeader';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';
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
    }
  );
  const drinks = data?.pages?.map((page) => page.data).flat() ?? [];
  const totalSize = data?.pages[0].pageInfo?.totalSize;

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting && hasNextPage) {
        fetchNextPage();
      }
    });
  });

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    if (infinityPollRef.current) {
      observer.observe(infinityPollRef.current);
    }
  }, [infinityPollRef.current]);

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
