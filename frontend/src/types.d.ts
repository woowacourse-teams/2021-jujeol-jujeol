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
