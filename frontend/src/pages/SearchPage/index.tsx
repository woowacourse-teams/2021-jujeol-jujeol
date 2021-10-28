import { Link } from 'react-router-dom';
import { css } from '@emotion/react';

import {
  BeerColorIcon,
  CategoryEtcColorIcon,
  CocktailColorIcon,
  MakgeolliColorIcon,
  SojuColorIcon,
  WineColorIcon,
  YangjuColorIcon,
} from 'src/components/@Icons';
import AllIcon from 'src/components/@Icons/AllIcon';
import Grid from 'src/components/@shared/Grid/Grid';
import Heading from 'src/components/@shared/Heading/Heading';
import Banner from 'src/components/Banner/Banner';
import SearchBar from 'src/components/SearchBar/SearchBar';
import { PATH } from 'src/constants';
import usePageTitle from 'src/hooks/usePageTitle';
import { hiddenStyle } from 'src/styles/hidden';
import { Categories, CategoryItem, Container } from './styles';

const categories: Category[] = [
  {
    key: 'BEER',
    name: '맥주',
    Icon: BeerColorIcon,
  },
  {
    key: 'SOJU',
    name: '소주',
    Icon: SojuColorIcon,
  },
  {
    key: 'MAKGEOLLI',
    name: '막걸리',
    Icon: MakgeolliColorIcon,
  },
  {
    key: 'WINE',
    name: '와인',
    Icon: WineColorIcon,
  },
  {
    key: 'YANGJU',
    name: '양주',
    Icon: YangjuColorIcon,
  },
  {
    key: 'COCKTAIL',
    name: '칵테일',
    Icon: CocktailColorIcon,
  },
  {
    key: 'ETC',
    name: '기타',
    Icon: CategoryEtcColorIcon,
  },
  {
    key: 'ALL',
    name: '전체보기',
    Icon: AllIcon,
  },
];

export { categories };

const SearchPage = () => {
  usePageTitle('검색');

  return (
    <Container>
      <Heading.level1 css={hiddenStyle}>주절주절</Heading.level1>
      <Heading.level2 css={hiddenStyle}>검색페이지</Heading.level2>
      <SearchBar placeholder="검색어를 입력해주세요" readOnly={false} />

      <Categories>
        <Heading.level3
          css={css`
            margin: 1rem 0.5rem;
          `}
        >
          카테고리
        </Heading.level3>
        <Grid
          col={4}
          colGap="0.5rem"
          rowGap="1rem"
          justifyItems="center"
          title="주류 카테고리 목록"
        >
          {categories.map(({ key, name, Icon }) => {
            return (
              <CategoryItem key={key} title={name}>
                <Link to={`${PATH.DRINKS}?category=${key}`} title={name}>
                  <Icon />
                  <span>{name}</span>
                </Link>
              </CategoryItem>
            );
          })}
        </Grid>
      </Categories>

      <Banner
        type="IMAGE"
        title="프로모션 배너"
        src="https://dmaxaug2ve9od.cloudfront.net/promotion_banner/jujeol-promotion-cheers.png"
        alt="이 건배사 어때요? 이멤버 리멤버!"
      />
    </Container>
  );
};

export default SearchPage;
