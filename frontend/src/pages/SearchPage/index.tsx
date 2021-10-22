import { Link } from 'react-router-dom';
import Grid from 'src/components/@shared/Grid/Grid';
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
import SearchBar from 'src/components/@shared/SearchBar/SearchBar';
import Banner from 'src/components/Banner/Banner';
import { PATH } from 'src/constants';
import { Container, Categories, CategoryItem } from './styles';
import Heading from 'src/components/@shared/Heading/Heading';
import { css } from '@emotion/react';

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
  return (
    <Container>
      <Heading.level2
        css={css`
          width: 0;
          height: 0;
          opacity: 0;
        `}
      >
        검색페이지
      </Heading.level2>
      <SearchBar placeholder="검색어를 입력해주세요" readOnly={false} />

      <Categories>
        <Heading.level3
          css={css`
            margin: 1rem 0.5rem;
          `}
        >
          카테고리
        </Heading.level3>
        <Grid col={4} colGap="0.5rem" rowGap="1rem" justifyItems="center">
          {categories.map(({ key, name, Icon }) => {
            return (
              <CategoryItem key={key}>
                <Link to={`${PATH.DRINKS}?category=${key}`}>
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
