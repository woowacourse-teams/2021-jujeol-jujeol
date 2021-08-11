import { RouteComponentProps } from 'react-router-dom';
import Grid from 'src/components/@shared/Grid/Grid';
import {
  BeerColorIcon,
  CategoryEtcColorIcon,
  CocktailColorIcon,
  MakgeolliColorIcon,
  SojuColorIcon,
  WineColorIcon,
  YangjuColorIcon,
} from 'src/components/@shared/Icons';
import SearchBar from 'src/components/@shared/SearchBar/SearchBar';
import Banner from 'src/components/Banner/Banner';
import { PATH } from 'src/constants';
import { Container, Categories, CategoryItem } from './styles';

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
];

export { categories };

const SearchPage = ({ history }: RouteComponentProps) => {
  const onMoveToSearchResult = (key: string) => () => {
    history.push(`${PATH.SEARCH_RESULT}?category=${key}`);
  };

  return (
    <Container>
      <SearchBar placeholder="검색어를 입력해주세요" readOnly={false} />

      <Categories>
        <h3>카테고리</h3>
        <Grid col={4} colGap="0.5rem" rowGap="1rem" justifyItems="center">
          {categories.map(({ key, name, Icon }) => {
            return (
              <CategoryItem key={key} onClick={onMoveToSearchResult(key)}>
                <Icon />
                <span>{name}</span>
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
