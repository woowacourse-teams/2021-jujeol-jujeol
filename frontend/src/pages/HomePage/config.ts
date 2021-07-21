interface ItemList {
  sectionType: 'ITEM_LIST';
  type: 'CARD' | 'LIST';
  query: {
    theme?: string;
  };
  title: string;
  subTitle?: string;
  count?: number;
  isShowMoreEnabled?: boolean;
}

interface Banner {
  sectionType: 'BANNER';
  type: 'IMAGE';
  title: 'Promotion banner';
  src: string;
  alt: string;
}

type HomePageConfig = (ItemList | Banner)[];

const config: HomePageConfig = [
  {
    sectionType: 'ITEM_LIST',
    type: 'CARD',
    query: {
      theme: 'recommendation',
    },
    title: '오늘 이런 술 어때요?',
    subTitle: '내 취향과 가까운 술을 준비했어요',
    count: 7,
    isShowMoreEnabled: false,
  },
  {
    sectionType: 'ITEM_LIST',
    type: 'LIST',
    query: {
      theme: 'recommendation',
    },
    title: '전체보기',
    count: 3,
    isShowMoreEnabled: true,
  },
  {
    sectionType: 'BANNER',
    type: 'IMAGE',
    title: 'Promotion banner',
    src: 'https://github.com/sunhpark42/test_asset/blob/master/jujeol-promotion-cheers.png',
    alt: '이 건배사 어때요? 이멤버 리멤버!',
  },
];

export default config;
export type { HomePageConfig };
