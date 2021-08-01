import MainHeader from 'src/components/Header/MainHeader';
import Grid from 'src/components/@shared/Grid/Grid';
import DrinkListSection from 'src/pages/HomePage/DrinkListSection';
import config, { ItemList, Banner as BannerType } from './config';
import Banner from 'src/components/Banner/Banner';
import { useHistory } from 'react-router-dom';
import { PATH } from 'src/constants';
import SearchBar from 'src/components/@shared/SearchBar/SearchBar';

const HomePage = () => {
  const history = useHistory();

  const onMoveToSearchPage = () => history.push(PATH.SEARCH);

  return (
    <div>
      <MainHeader />
      <SearchBar onClick={onMoveToSearchPage} placeholder="검색어를 입력해주세요" />

      <Grid rowGap="2rem" colMin="280px" colMax="480px">
        {config.map((section: ItemList | BannerType) => {
          if (section.sectionType === 'ITEM_LIST') {
            const {
              id,
              type,
              theme,
              title,
              titleAlign,
              subTitle,
              query,
              isShowMoreEnabled,
              showMoreLink,
              count,
            }: ItemList = section;

            return (
              <li key={id}>
                <DrinkListSection
                  type={type as 'CARD' | 'LIST'}
                  theme={theme}
                  title={title}
                  titleAlign={titleAlign}
                  subTitle={subTitle}
                  query={query}
                  isShowMoreEnabled={isShowMoreEnabled}
                  showMoreLink={showMoreLink}
                  count={count}
                />
              </li>
            );
          }

          if (section.sectionType === 'BANNER') {
            const { id, type, title, src, alt }: BannerType = section;

            return (
              <li key={id}>
                <Banner
                  type={type as 'IMAGE'}
                  title={title as '프로모션 배너'}
                  src={src}
                  alt={alt}
                />
              </li>
            );
          }
        })}
      </Grid>
    </div>
  );
};

export default HomePage;
