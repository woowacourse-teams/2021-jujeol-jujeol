declare interface Category {
  key: 'BEER' | 'SOJU' | 'MAKGEOLLI' | 'WINE' | 'YANGJU' | 'COCKTAIL' | 'ETC';
  name: string;
  Icon: (props: IconProps) => React.ReactElement;
}

declare namespace Drink {
  interface Item {
    id: number;
    name: string;
    alcoholByVolume: number;
    imageUrl: string;
  }

  interface DetailItem {
    name: string;
    englishName: string;
    imageUrl: string;
    category: Category;
    alcoholByVolume: number;
    preferenceRate: number;
    preferenceAvg: number;
  }

  interface PersonalDrinkItem extends Pick<Item, 'id' | 'name' | 'imageUrl'> {
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
    content: string;
  }
}

declare interface IconProps {
  color?: string;
  width?: string;
  height?: string;
}
