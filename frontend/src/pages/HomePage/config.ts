import CATEGORY from 'src/constants/category';
import idGenerator from 'src/utils/idGenerator';
import { getRandomNumbers } from 'src/utils/random';

const drinks = [
  {
    queryKey: 'beer-best',
    title: '맥주 BEST',
    query: {
      category: CATEGORY.BEER,
    },
  },
  {
    queryKey: 'soju-best',
    title: '소주 BEST',
    query: {
      category: CATEGORY.SOJU,
    },
  },
  {
    queryKey: 'makgeolli-best',
    title: '막걸리 BEST',
    query: {
      category: CATEGORY.MAKGEOLLI,
    },
  },
  {
    queryKey: 'wine-best',
    title: '와인 BEST',
    query: {
      category: CATEGORY.WINE,
    },
  },
  {
    queryKey: 'cocktail-best',
    title: '칵테일 BEST',
    query: {
      category: CATEGORY.COCKTAIL,
    },
  },
  {
    queryKey: 'yangju-best',
    title: '양주 BEST',
    query: {
      category: CATEGORY.YANGJU,
    },
  },
  {
    queryKey: 'etc-best',
    title: '기타 주류 BEST',
    query: {
      category: CATEGORY.ETC,
    },
  },
];

const getBestDrinksConfig = ({
  queryKey,
  title,
  subTitle,
  query,
}: Pick<
  Config.HomePageItemList,
  'queryKey' | 'title' | 'subTitle' | 'query'
>): Config.HomePageItemList => ({
  id: idGenerator(),
  sectionType: 'ITEM_LIST',
  type: 'LIST',
  queryKey,
  query: { ...query },
  title,
  subTitle,
  titleAlign: 'left',
  count: 3,
});

const sharedConfig: Config.HomePageConfig = [
  {
    id: idGenerator(),
    sectionType: 'BANNER',
    type: 'IMAGE',
    title: '프로모션 배너',
    src: 'https://d1sqi8cd60mbpv.cloudfront.net/promotion_banner/jujeol-promotion-cheers.png',
    alt: '이 건배사 어때요? 이멤버 리멤버!',
  },
];

const topContentConfig = ({
  isLoggedIn = false,
}: {
  isLoggedIn: boolean;
}): Config.HomePageItemList => ({
  id: idGenerator(),
  sectionType: 'ITEM_LIST',
  type: 'CARD',
  queryKey: 'main-top-content',
  query: { sortBy: isLoggedIn ? 'expectedPreference' : 'preferenceAvg' },
  title: '오늘 이런 술 어때요?',
  titleAlign: 'center',
  subTitle: isLoggedIn
    ? '회원님과 비슷한 취향을 가진 회원님들이 좋아하는 술이에요'
    : '주절주절 선호도 BEST',
  count: 7,
  isShowMoreEnabled: false,
});

const getHomePageConfig = ({
  isLoggedIn = false,
}: {
  isLoggedIn: boolean;
}): Config.HomePageConfig => {
  const bestDrinkIndexes = getRandomNumbers({ min: 0, max: 6, count: 2 });
  const bestDrinkConfigs = bestDrinkIndexes.map((index) => {
    const config = getBestDrinksConfig(drinks[index]);
    return {
      ...config,
      query: { ...config.query, sortBy: 'preferenceAvg' },
    };
  });

  return [topContentConfig({ isLoggedIn }), ...bestDrinkConfigs, ...sharedConfig];
};

export default getHomePageConfig;
