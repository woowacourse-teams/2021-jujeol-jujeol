import { useQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import { PATH } from 'src/constants';

import { DizzyEmojiColorIcon } from 'src/components/@shared/Icons';
import { Img } from 'src/components/@shared/Image/Image';
import CardList from 'src/components/List/CardList';
import Section from 'src/components/Section/Section';

import { Item, NotificationSection } from './NoSearchResult.styles';

const NoSearchResults = ({ search }: { search: string }) => {
  const history = useHistory();

  const { data: { data: drinks } = [] } = useQuery('recommendedDrinks', () =>
    API.getRecommendedDrinks()
  );

  const onMoveToDrinkDetail = (id: number) => () => {
    history.push(`${PATH.DRINKS}/${id}`);
  };

  return (
    <div>
      <NotificationSection>
        <DizzyEmojiColorIcon />
        <h2>{`${search}에 대한 검색 결과가 없습니다.`}</h2>
      </NotificationSection>

      <Section title="이런 술은 어때요?" titleAlign="left">
        <CardList count={7} colGap="1rem">
          {drinks?.map(({ id, name, imageUrl }: { id: number; name: string; imageUrl: string }) => (
            <Item key={id} onClick={onMoveToDrinkDetail(id)}>
              <Img src={imageUrl} alt={name} size="MEDIUM" shape="CIRCLE" />
              <p>{name}</p>
            </Item>
          ))}
        </CardList>
      </Section>
    </div>
  );
};

export default NoSearchResults;
