import MainHeader from 'src/components/Header/MainHeader';
import Grid from 'src/components/@shared/Grid/Grid';
import ItemListSection from 'src/components/Section/ItemListSection';
import BannerSection from 'src/components/Section/BannerSection';
import config, { ItemList, Banner } from './config';

const HomePage = () => {
  return (
    <div>
      <MainHeader />
      <Grid rowGap="2rem" colMin="280px" colMax="420px">
        {config.map((section: ItemList | Banner) => {
          if (section.sectionType === 'ITEM_LIST') {
            const {
              sectionType,
              type,
              title,
              titleAlign,
              subTitle,
              query,
              isShowMoreEnabled,
              showMoreLink,
              count,
            }: ItemList = section;

            return (
              <li key={title}>
                <ItemListSection
                  sectionType={sectionType}
                  type={type as 'CARD' | 'LIST'}
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
            const { sectionType, type, title, src, alt }: Banner = section;

            return (
              <li>
                <BannerSection
                  sectionType={sectionType}
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
