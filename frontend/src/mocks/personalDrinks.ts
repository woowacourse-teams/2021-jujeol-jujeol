const personalDrinks = {
  data: [
    {
      id: 3,
      name: '타이거 라들러 레몬',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_008.png',
      preferenceRate: 4.5,
    },
    {
      id: 2,
      name: '스텔라',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-09_001.png',
      preferenceRate: 3.0,
    },
    {
      id: 1,
      name: '오비',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_007.png',
      preferenceRate: 2.4,
    },
    {
      id: 4,
      name: '기네스',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_008.png',
      preferenceRate: 2.5,
    },
    {
      id: 5,
      name: '타이거 라들러 자몽',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_008.png',
      preferenceRate: 4.5,
    },
    {
      id: 6,
      name: '제주맥주',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-09_001.png',
      preferenceRate: 3.0,
    },
    {
      id: 7,
      name: '곰표맥주',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_007.png',
      preferenceRate: 2.4,
    },
    {
      id: 8,
      name: '칭따오',
      imageUrl: '/KakaoTalk_Image_2021-07-08-19-58-22_008.png',
      preferenceRate: 2.5,
    },
  ],
  pageInfo: {
    currentPage: 1,
    lastPage: 1,
    countPerPage: 10,
    totalSize: 8,
  },
};

const noPersonalDrink = {
  data: [],
  pageInfo: {
    currentPage: 1,
    lastPage: 1,
    countPerPage: 10,
    totalSize: 0,
  },
};

export { personalDrinks, noPersonalDrink };
