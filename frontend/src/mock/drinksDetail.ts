const drinksDetail = {
  id: 0,
  name: '기네스',
  englishName: 'Stella Artois',
  imageUrl: 'https://dmaxaug2ve9od.cloudfront.net/stella_artois.png',
  category: {
    id: 0,
    name: '맥주',
  },
  alcoholByVolume: '3.4',
  preferenceRate: 3.5,
};

const drinksReviews = [
  {
    id: 0,
    author: {
      id: 0,
      name: 'tyche',
    },
    content: `대통령은 법률이 정하는 바에 의하여 사면·감형 또는 복권을 명할 수 있다. 감사원은 원장을 포함한 5인 이상 11인 이하의 감사위원으로 구성한다. 정부는 회계연도마다 예산안을 편성하여 회계연도 개시 90일전까지 국회에 제출하고, 국회는 회계연도 개시 30일전까지 이를 의결하여야 한다. 대법원에 대법관을 둔다. 다만, 법률이 정하는 바에 의하여 대법관이 아닌 법관을 둘 수 있다.대통령은 법률이 정하는 바에 의하여 사면·감형 또는 복권을 명할 수 있다. 감사원은 원장을 포함한 5인 이상 11인 이하의 감사위원으로 구성한다. 아아아아아아`,
    createdAt: new Date(),
    modifiedAt: new Date(),
  },
  {
    id: 1,
    author: {
      id: 0,
      name: 'sunny',
    },
    content:
      '너무 맛있어요! 완전 제 최애 맥주.치킨과함께 먹으면 담백하고, 탄산도 적절히 있어서 좋아요.',
    createdAt: new Date(),
    modifiedAt: new Date(),
  },
];

export { drinksDetail, drinksReviews };
