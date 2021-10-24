import { useContext } from 'react';
import { useHistory } from 'react-router-dom';

import Grid from 'src/components/@shared/Grid/Grid';
import Banner from 'src/components/Banner/Banner';
import MainHeader from 'src/components/Header/MainHeader';
import SearchBar from 'src/components/SearchBar/SearchBar';
import { PATH } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import usePageTitle from 'src/hooks/usePageTitle';
import DrinkListSection from 'src/pages/HomePage/DrinkListSection';
import getHomePageConfig from './config';

const HomePage = () => {
  const history = useHistory();
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  const onMoveToSearchPage = () => history.push(PATH.SEARCH);

  usePageTitle();

  return (
    <div>
      <MainHeader />
      <SearchBar onClick={onMoveToSearchPage} placeholder="검색어를 입력해주세요" readOnly={true} />

      <Grid rowGap="2rem" colMin="280px" colMax="480px">
        {getHomePageConfig({ isLoggedIn }).map(
          (section: Config.HomePageItemList | Config.HomePageBanner) => {
            if (section.sectionType === 'ITEM_LIST') {
              const {
                id,
                type,
                queryKey,
                title,
                titleAlign,
                subTitle,
                query,
                isShowMoreEnabled,
                showMoreLink,
                count,
              }: Config.HomePageItemList = section;

              return (
                <li key={id}>
                  <DrinkListSection
                    type={type as 'CARD' | 'LIST'}
                    queryKey={queryKey}
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
              const { id, type, title, src, alt }: Config.HomePageBanner = section;

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
          }
        )}
      </Grid>
    </div>
  );
};

export default HomePage;
