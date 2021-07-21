import { useQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import List from 'src/components/Section/List';
import ListItem from 'src/components/Section/ListItem';
import { PATH } from 'src/constants';
import { Title } from './ViewAllPage.styles';

const ViewAllPage = () => {
  const { data: { data: drinks } = [] } = useQuery('drinks', () => API.getDrinks(), {
    retry: 1,
  });
  const history = useHistory();

  const goBack = () => {
    history.goBack();
  };

  return (
    <div>
      <Title>
        <button type="button" onClick={goBack}>
          {'<'}
        </button>
        <h1>전체보기</h1>
      </Title>
      <List count={drinks?.length}>
        {drinks?.map((item: ItemList.Drinks) => (
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
    </div>
  );
};

export default ViewAllPage;
