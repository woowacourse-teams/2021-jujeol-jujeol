import { useQuery } from 'react-query';
import API from 'src/apis/requests';
import List from '../../components/List/List';
import Section from '../../components/Section/Section';
import { useHistory } from 'react-router-dom';
import { PATH } from 'src/constants';
import CardList from 'src/components/List/CardList';
import CardItem from 'src/components/Item/CardItem';
import ListItem from 'src/components/Item/ListItem';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';

interface Props {
  type: 'CARD' | 'LIST';
  queryKey: string;
  query: {
    category?: string;
  };
  title: string;
  titleAlign?: 'left' | 'center' | 'right';
  subTitle?: string;
  count?: number;
  isShowMoreEnabled?: boolean;
  showMoreLink?: string;
}

const DrinkListSection = ({
  type,
  queryKey,
  query,
  title,
  titleAlign = 'left',
  subTitle = '',
  count,
  isShowMoreEnabled = true,
  showMoreLink,
}: Props) => {
  const queryParams = new URLSearchParams(query);

  const { data: { data: drinks } = [], isLoading } = useQuery(queryKey, () =>
    API.getDrinks({ page: 1, params: queryParams })
  );
  const history = useHistory();

  return (
    <Section
      title={title}
      titleAlign={titleAlign}
      subTitle={subTitle}
      isShowMoreEnabled={isShowMoreEnabled}
      showMoreLink={showMoreLink}
    >
      {type === 'CARD' && (
        <CardList count={count ?? drinks?.length}>
          {drinks?.slice(0, count ?? drinks.length).map((item: Drink.Item) => (
            <CardItem
              key={item?.id}
              imageUrl={item?.imageResponse?.small}
              title={item?.name}
              description={`도수: ${item?.alcoholByVolume}%`}
              preferenceType={
                item?.preferenceRate ? 'MY' : item?.expectedPreference ? 'EXPECTED' : 'AVG'
              }
              preferenceRate={
                item?.preferenceRate || item?.expectedPreference || item?.preferenceAvg
              }
              onClick={() => {
                history.push(`${PATH.DRINKS}/${item?.id}`);
              }}
            />
          ))}
          {isLoading &&
            Array.from({ length: 3 }).map((_, index) => (
              <Skeleton key={index} type="CARD" size="X_LARGE" />
            ))}
        </CardList>
      )}

      {type === 'LIST' && (
        <List count={count ?? drinks?.length}>
          {drinks?.slice(0, count ?? drinks.length).map((item: Drink.Item) => (
            <ListItem
              key={item?.id}
              imageUrl={item?.imageResponse?.small}
              title={item?.name}
              description={`도수: ${item?.alcoholByVolume}%`}
              preferenceType={
                item?.preferenceRate ? 'MY' : item?.expectedPreference ? 'EXPECTED' : 'AVG'
              }
              preferenceRate={
                item?.preferenceRate || item?.expectedPreference || item?.preferenceAvg
              }
              onClick={() => {
                history.push(`${PATH.DRINKS}/${item?.id}`);
              }}
            />
          ))}
          {isLoading && <ListItemSkeleton count={3} />}
        </List>
      )}
    </Section>
  );
};

export default DrinkListSection;
