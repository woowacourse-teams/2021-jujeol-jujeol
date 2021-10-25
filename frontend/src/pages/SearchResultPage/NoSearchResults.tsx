import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';

import API from 'src/apis/requests';
import { DizzyEmojiColorIcon } from 'src/components/@Icons';
import { Img } from 'src/components/@shared/Image/Image';
import CardList from 'src/components/List/CardList';
import Section from 'src/components/Section/Section';
import { PATH } from 'src/constants';
import QUERY_KEY from 'src/constants/queryKey';
import { Item, NotificationSection } from './NoSearchResult.styles';

const NoSearchResults = ({ search }: { search: string }) => {
  const { data: { data: drinks } = [] } = useQuery(QUERY_KEY.DRINK_LIST_SORTED_BY_PREFERENCE, () =>
    API.getDrinks({
      page: 1,
      params: new URLSearchParams('sortBy=preferenceAvg&size=7'),
    })
  );

  return (
    <>
      <NotificationSection>
        <DizzyEmojiColorIcon />
        <p>{`${search}에 대한 검색 결과가 없습니다.`}</p>
      </NotificationSection>

      <Section title="이런 술은 어때요?" titleAlign="left">
        <CardList count={7} colGap="1rem" title="검색 결과가 없을 때 술 추천 리스트">
          {drinks?.map(
            ({ id, name, imageResponse }: Pick<Drink.Item, 'id' | 'name' | 'imageResponse'>) => (
              <Item key={id}>
                <Link to={`${PATH.DRINKS}/${id}`}>
                  <Img src={imageResponse?.small} alt={name} size="MEDIUM" shape="CIRCLE" />
                  <p>{name}</p>
                </Link>
              </Item>
            )
          )}
        </CardList>
      </Section>
    </>
  );
};

export default NoSearchResults;
