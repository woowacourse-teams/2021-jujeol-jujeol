declare interface Category {
  key: 'BEER' | 'SOJU' | 'MAKGEOLLI' | 'WINE' | 'YANGJU' | 'COCKTAIL' | 'ETC' | 'ALL';
  name: string;
  Icon: (props: IconProps) => React.ReactElement;
}

declare namespace Drink {
  interface Item {
    id: number;
    name: string;
    englishName: string;
    imageResponse: { small: string; medium: string; large: string };
    description: string;
    category: Category;
    alcoholByVolume: number;
    preferenceRate: number;
    preferenceAvg: number;
    expectedPreference: number;
  }

  interface DetailItem {
    name: string;
    englishName: string;
    imageResponse: { small: string; medium: string; large: string };
    description: string;
    category: Category;
    alcoholByVolume: number;
    preferenceRate: number;
    preferenceAvg: number;
  }

  interface PersonalDrinkItem extends Pick<Item, 'id' | 'name'> {
    imageUrl: string;
    preferenceRate: number;
  }

  type List = Item[];
}

declare namespace Review {
  interface Item {
    id: number;
    content: string;
    author: {
      id: number;
      name: string;
    };
    createdAt: Date;
    modifiedAt: Date | null;
  }

  interface PersonalReviewItem extends Omit<Item, 'author'> {
    drink: {
      drinkId: number;
      name: string;
      imageUrl: string;
    };
  }

  type List = ReviewItem[];

  interface PostRequestData {
    drinkId: number;
    content: string;
  }
}

declare interface IconProps {
  color?: string;
  width?: string;
  height?: string;
}

declare namespace Config {
  interface HomePageItemList {
    id: number;
    sectionType: 'ITEM_LIST';
    type: 'CARD' | 'LIST';
    queryKey: string;
    query: {
      category?: string;
      sortBy?: string;
    };
    title: string;
    titleAlign: 'left' | 'center' | 'right';
    subTitle?: string;
    count?: number;
    isShowMoreEnabled?: boolean;
    showMoreLink?: string;
  }

  interface HomePageBanner {
    id: number;
    sectionType: 'BANNER';
    type: 'IMAGE';
    title: '프로모션 배너';
    src: string;
    alt: string;
  }

  type HomePageConfig = (HomePageItemList | HomePageBanner)[];
}
