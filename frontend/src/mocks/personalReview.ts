const personalReviews = {
  data: [
    {
      id: 3,
      content: '아따! 맛있구마잉',
      createdAt: '2021-08-04T13:15:58.726196',
      modifiedAt: null,
      drink: {
        drinkId: 2,
        name: '스텔라',
        imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-09_001.png',
      },
    },
    {
      id: 2,
      content: '워메 맛있는 거~',
      createdAt: '2021-08-04T13:15:58.696947',
      modifiedAt: null,
      drink: {
        drinkId: 3,
        name: '애플',
        imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-20_006.png',
      },
    },
    {
      id: 1,
      content: '크으 맛난다잉~>?',
      createdAt: '2021-08-04T13:15:58.670223',
      modifiedAt: null,
      drink: {
        drinkId: 1,
        name: '오비',
        imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_007.png',
      },
    },
    {
      id: 4,
      content: '크으 맛난다잉~>?',
      createdAt: '2021-08-04T13:15:58.670220',
      modifiedAt: null,
      drink: {
        drinkId: 4,
        name: '기네스',
        imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_008.png',
      },
    },
  ],
  pageInfo: {
    currentPage: 1,
    lastPage: 1,
    countPerPage: 10,
    totalSize: 3,
  },
};

const noPersonalReviews = {
  data: [],
  pageInfo: {
    currentPage: 1,
    lastPage: 1,
    countPerPage: 10,
    totalSize: 0,
  },
};

export { personalReviews, noPersonalReviews };
