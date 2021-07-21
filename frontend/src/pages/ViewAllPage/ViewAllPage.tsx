import { useQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import List from 'src/components/Section/List';
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
      <List items={drinks} count={drinks?.length} />
    </div>
  );
};

export default ViewAllPage;
