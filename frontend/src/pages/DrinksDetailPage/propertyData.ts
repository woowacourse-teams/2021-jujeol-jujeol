import React from 'react';

import {
  BeerIcon,
  CategoryEtcIcon,
  CocktailIcon,
  DizzyEmojiIcon,
  ExcitedEmojiIcon,
  MakgeolliIcon,
  SmileEmojiIcon,
  SojuIcon,
  WineIcon,
  YangjuIcon,
} from 'src/components/@Icons';

const categories: {
  [key: string]: { content: string; Icon: (props: IconProps) => React.ReactElement };
} = {
  BEER: {
    content: '맥주',
    Icon: BeerIcon,
  },
  SOJU: {
    content: '소주',
    Icon: SojuIcon,
  },
  MAKGEOLLI: {
    content: '막걸리',
    Icon: MakgeolliIcon,
  },
  WINE: {
    content: '와인',
    Icon: WineIcon,
  },
  YANGJU: {
    content: '양주',
    Icon: YangjuIcon,
  },
  COCKTAIL: {
    content: '칵테일',
    Icon: CocktailIcon,
  },
  ETC: {
    content: '기타',
    Icon: CategoryEtcIcon,
  },
};

const getCategoryProperty = (key: string) => categories[key] ?? categories.ETC;

const getAbvIcon = (abv: number) => {
  if (abv <= 5) {
    return SmileEmojiIcon;
  }

  if (abv <= 20) {
    return ExcitedEmojiIcon;
  }

  if (abv <= 40) {
    return DizzyEmojiIcon;
  }

  return DizzyEmojiIcon;
};

const getAbvProperty = (abv: number) => ({
  content: `${abv}%`,
  Icon: getAbvIcon(abv),
});

const properties = [
  {
    id: 0,
    name: '주종',
    getProperty: ({ categoryKey }: { categoryKey: string }) => getCategoryProperty(categoryKey),
  },
  {
    id: 1,
    name: '도수',
    getProperty: ({ alcoholByVolume }: { alcoholByVolume: number }) =>
      getAbvProperty(alcoholByVolume),
  },
];

export { properties };
