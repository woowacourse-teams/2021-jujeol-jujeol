declare namespace Review {
  export type ReviewItem = {
    id: number;
    content: string;
    author: {
      id: number;
    };
    createdAt: Date;
    modifiedAt: Date | null;
  };

  export interface ReviewList {
    reviews: ReviewItem[];
  }

  export type ReviewRequestData = {
    content: string;
  };
}

declare namespace Category {
  interface CategoryItem {
    key: string;
    name: string;
    Icon: (props: IconProps) => React.ReactElement;
  }
}

declare namespace ItemList {
  interface Drinks {
    id: number;
    name: string;
    imageUrl: string;
    alcoholByVolume: number;
  }
}

declare namespace MyDrink {
  interface MyDrinkItem {
    id: number;
    name: string;
    imageUrl: string;
    preferenceRate: number;
  }
}

declare namespace PersonalReview {
  interface PersonalReviewItem {
    id: number;
    drink: {
      drinkId: number;
      name: string;
      imageUrl: string;
    };
    content: string;
    createdAt: Date;
    modifiedAt: Date | null;
  }
}

declare namespace SearchResult {
  interface SearchResultItem {
    id: number;
    name: string;
    alcoholByVolume: number;
    imageUrl: string;
  }

  type SearchResultList = SearchResultItem[];
}
declare interface IconProps {
  color?: string;
  width?: string;
  height?: string;
}
