import Grid from 'src/components/@shared/Grid/Grid';
import SearchBar from 'src/components/@shared/SearchBar/SearchBar';
import Banner from 'src/components/Banner/Banner';
import HumanIcon from 'src/components/Icons/human';
import { COLOR } from 'src/constants';
import { Container, Categories, CategoryItem } from './styles';

const SearchPage = () => {
  return (
    <Container>
      <SearchBar />

      <Categories>
        <h3>카테고리</h3>
        <Grid col={4} colGap="0.5rem">
          {Array.from({ length: 7 }).map((_, index) => {
            return (
              <CategoryItem key={index}>
                <HumanIcon color={COLOR.BLACK_900} />
                <span>술술술</span>
              </CategoryItem>
            );
          })}
        </Grid>
      </Categories>

      <Banner
        type="IMAGE"
        title="프로모션 배너"
        src="https://github.com/sunhpark42/test_asset/blob/master/jujeol-promotion-cheers.png?raw=true"
        alt="이 건배사 어때요? 이멤버 리멤버!"
      />
    </Container>
  );
};

export default SearchPage;
