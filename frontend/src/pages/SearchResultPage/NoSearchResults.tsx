import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';
import API from 'src/apis/requests';
import { PATH } from 'src/constants';

import { DizzyEmojiColorIcon } from 'src/components/@Icons';
import { Img } from 'src/components/@shared/Image/Image';
import CardList from 'src/components/List/CardList';
import Section from 'src/components/Section/Section';

import { Item, NotificationSection } from './NoSearchResult.styles';

const NoSearchResults = ({ search }: { search: string }) => {
  const { data: { data: drinks } = [] } = useQuery('recommendedDrinks', () =>
    API.getRecommendedDrinks()
  );

  return (
    <>
      <NotificationSection>
        <DizzyEmojiColorIcon />
        <p>{`${search}에 대한 검색 결과가 없습니다.`}</p>
      </NotificationSection>

      <Section title="이런 술은 어때요?" titleAlign="left">
        <CardList count={7} colGap="1rem">
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
