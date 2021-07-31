import { useRef, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import Arrow from 'src/components/@shared/Arrow/Arrow';

import Header from 'src/components/@shared/Header/Header';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import ListItem from 'src/components/Item/ListItem';
import List from 'src/components/List/List';
import { PATH } from 'src/constants';

import { Container, Title, Section } from './styles';

const defaultSearchResult = [
  {
    id: 0,
    name: 'name',
    alcoholByVolume: 0,
    imageUrl: 'https://fakeimg.pl/88x88',
  },
];

const SearchResultPage = ({ history, location }: RouteComponentProps) => {
  const infinityPollRef = useRef(null);

  const [searchResult, setSearchResult] = useState(defaultSearchResult);

  const words = new URLSearchParams(location.search).get('words');

  const onMoveGoBack = () => history.goBack();

  return (
    <Container>
      <Header>
        <Title>
          <button type="button" onClick={onMoveGoBack}>
            <Arrow size="0.7rem" borderWidth="2px" dir="LEFT" />
          </button>
          <h1>검색결과 {searchResult.length}건</h1>
        </Title>
      </Header>

      <Section>
        <h2>
          <strong>{words}</strong>로 검색한 결과입니다.
        </h2>

        <List count={searchResult?.length}>
          {searchResult?.map((item: ItemList.Drinks) => (
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
        <InfinityScrollPoll ref={infinityPollRef} />
      </Section>
    </Container>
  );
};

export default SearchResultPage;
