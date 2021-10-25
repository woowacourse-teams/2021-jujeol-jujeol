import { useContext } from 'react';
import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';

import API from 'src/apis/requests';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import CardItem from 'src/components/Item/CardItem';
import ListItem from 'src/components/Item/ListItem';
import CardList from 'src/components/List/CardList';
import ListItemSkeleton from 'src/components/Skeleton/ListItemSkeleton';
import { ERROR_MESSAGE, PATH } from 'src/constants';
import List from '../../components/List/List';
import Section from '../../components/Section/Section';

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

  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const { data: { data: drinks } = [], isLoading } = useQuery(
    queryKey,
    () => API.getDrinks({ page: 1, params: queryParams }),
    {
      onError: (error: Request.Error) => {
        setSnackbarMessage?.({
          type: 'ERROR',
          message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT,
        });
      },
    }
  );

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
            <Link key={item?.id} to={`${PATH.DRINKS}/${item?.id}`}>
              <CardItem
                imageUrl={item?.imageResponse?.small}
                title={item?.name}
                description={`도수: ${item?.alcoholByVolume}%`}
                preferenceType={
                  item?.preferenceRate ? 'MY' : item?.expectedPreference ? 'EXPECTED' : 'AVG'
                }
                preferenceRate={
                  item?.preferenceRate || item?.expectedPreference || item?.preferenceAvg
                }
              />
            </Link>
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
              itemId={item?.id}
              imageUrl={item?.imageResponse?.small}
              title={item?.name}
              description={`도수: ${item?.alcoholByVolume}%`}
              preferenceType={
                item?.preferenceRate ? 'MY' : item?.expectedPreference ? 'EXPECTED' : 'AVG'
              }
              preferenceRate={
                item?.preferenceRate || item?.expectedPreference || item?.preferenceAvg
              }
            />
          ))}
          {isLoading && <ListItemSkeleton count={3} />}
        </List>
      )}
    </Section>
  );
};

export default DrinkListSection;
