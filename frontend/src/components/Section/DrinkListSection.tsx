import { useQuery } from 'react-query';
import API from 'src/apis/requests';
import CardList from './CardList';
import ListItem from './ListItem';
import List from './List';
import Section from './Section';
import { useHistory } from 'react-router-dom';
import { PATH } from 'src/constants';
import CardItem from './CardItem';

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
  const { data: { data: drinks } = [] } = useQuery('drinks', () => API.getDrinks(), {
    retry: 1,
  });
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
