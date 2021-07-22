import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import Header from 'src/components/Header/Header';
import List from 'src/components/Section/List';
import ListItem from 'src/components/Section/ListItem';
import { PATH } from 'src/constants';
import { Title } from './ViewAllPage.styles';

const ViewAllPage = () => {
  const { data, fetchNextPage } = useInfiniteQuery<{
    data: ItemList.Drinks[];
    pageInfo: { currentPage: number; lastPage: number; countPerPage: number; totalSize: number };
  }>('drinks', ({ pageParam }) => API.getDrinks({ pageParam }), {
    getNextPageParam: (lastPage) => {
      return lastPage?.pageInfo.currentPage < lastPage?.pageInfo.lastPage
        ? lastPage?.pageInfo.currentPage + 1
        : undefined;
    },
    retry: 1,
  });

  const [responseData] = data?.pages ?? [];
  const { data: drinks } = responseData ?? {};

  const history = useHistory();

  const goBack = () => {
    history.goBack();
  };

  return (
    <div>
      <Header>
        <Title>
          <button type="button" onClick={goBack}>
            {'<'}
          </button>
          <h1>전체보기</h1>
        </Title>
      </Header>
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
