import { useQuery } from 'react-query';
import API from 'src/apis/requests';
import List from '../../components/List/List';
import Section from '../../components/Section/Section';
import { useHistory } from 'react-router-dom';
import { PATH } from 'src/constants';
import CardList from 'src/components/List/CardList';
import CardItem from 'src/components/Item/CardItem';
import ListItem from 'src/components/Item/ListItem';

interface Props {
  type: 'CARD' | 'LIST';
  query: {
    theme?: string;
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
  query,
  title,
  titleAlign = 'left',
  subTitle = '',
  count,
  isShowMoreEnabled = true,
  showMoreLink,
}: Props) => {
  const queryParams = new URLSearchParams(query);
  const { data: { data: drinks } = [] } = useQuery('drinks', () =>
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
          {drinks?.slice(0, count ?? drinks.length).map((item: ItemList.Drinks) => (
            <CardItem
              key={item?.id}
              imageUrl={item?.imageUrl}
              title={item?.name}
              description={`도수: ${item?.alcoholByVolume}%`}
              onClick={() => {
                history.push(`${PATH.DRINKS}/${item?.id}`);
              }}
            />
          ))}
        </CardList>
      )}

      {type === 'LIST' && (
        <List count={count ?? drinks?.length}>
          {drinks?.slice(0, count ?? drinks.length).map((item: ItemList.Drinks) => (
            <ListItem
              key={item?.id}
              imageUrl={item?.imageUrl}
              title={item?.name}
              description={`도수: ${item?.alcoholByVolume}%`}
              onClick={() => {
                history.push(`${PATH.DRINKS}/${item?.id}`);
              }}
            />
          ))}
        </List>
      )}
    </Section>
  );
};

export default DrinkListSection;
