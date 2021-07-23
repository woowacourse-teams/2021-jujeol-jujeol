import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import Header from 'src/components/@shared/Header/Header';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import { PATH } from 'src/constants';
import { Title } from './ViewAllPage.styles';

const ViewAllPage = () => {
  const history = useHistory();

  const { data, fetchNextPage, hasNextPage } = useInfiniteQuery<{
    data: ItemList.Drinks[];
    pageInfo: { currentPage: number; lastPage: number; countPerPage: number; totalSize: number };
  }>('drinks', ({ pageParam = 1 }) => API.getDrinks({ page: pageParam }), {
    getNextPageParam: ({ pageInfo }) => {
      return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
    },
    retry: 1,
  });
  const drinks = data?.pages?.map((page) => page.data).flat() ?? [];

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

      {hasNextPage ? (
        <button
          type="button"
          onClick={() => fetchNextPage()}
          style={{ width: '100%', marginTop: '1rem' }}
        >
          더보기
        </button>
      ) : (
        <p style={{ width: '100%', marginTop: '1rem', padding: '1rem' }}>
          끝! 새로운 술이 추가 될 테니 기대해 주세요
        </p>
      )}
    </div>
  );
};

export default ViewAllPage;
