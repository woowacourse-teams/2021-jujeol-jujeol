import ArrowButton from '../@shared/ArrowButton/ArrowButton';
import EditButton from '../EditButton/EditButton';

import { Container, Header, Title, Content } from './TextReview.styles';

const TextReview = () => {
  return (
    <Container>
      <Header>
        <Title>KGB 라임</Title>
        <span>21. 02. 11</span>
        <EditButton />
      </Header>
      <Content>
        맛있게 잘 먹었습니다.맛있게 잘먹었습니다.!!!!!!!맛있게 잘 먹었습니다.맛있게 잘
        먹었습니다.맛있게 잘 먹었습니다.
      </Content>
      <ArrowButton size="0.4rem" borderSize="1.5px" dir="DOWN" />
    </Container>
  );
};

export default TextReview;
