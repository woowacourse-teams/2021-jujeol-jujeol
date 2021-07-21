import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';
import API from 'src/apis/requests';
import CardList from './CardList';
import { Title } from './ItemListSection.styles';
import List from './List';

interface Props {
  sectionType: 'ITEM_LIST';
  type: 'CARD' | 'LIST';
  query: {
    theme?: string;
  };
  title: string;
  titleAlign?: 'left' | 'center' | 'right';
  subTitle?: string;
  count?: number;
  isShowMoreEnabled?: boolean;
}

const ItemListSection = ({
  sectionType,
  type,
  query,
  title,
  titleAlign = 'left',
  subTitle = '',
  count,
  isShowMoreEnabled = true,
}: Props) => {
  const { data: { data: drinks } = [] } = useQuery('drinks', () => API.getDrinks(), {
    retry: 1,
  });

  return (
    <section>
      <Title textAlign={titleAlign} isShowMoreEnabled={isShowMoreEnabled}>
        <div>
          <h2>{title}</h2>
          {subTitle && <p>{subTitle}</p>}
        </div>
        {isShowMoreEnabled && <Link to="#">더보기 +</Link>}
      </Title>
      {type === 'CARD' && <CardList items={drinks} count={count ?? drinks?.length} />}
      {type === 'LIST' && <List items={drinks} count={count ?? drinks?.length} />}
    </section>
  );
};

export default ItemListSection;
