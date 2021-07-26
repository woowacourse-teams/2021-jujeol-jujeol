import ArrowButton from '../@shared/ArrowButton/ArrowButton';
import EditButton from '../EditButton/EditButton';

import { Container, Header, Title, Content } from './TextReview.styles';

interface Props {
  review: MyReview.MyReviewItem;
}

const TextReview = ({ review }: Props) => {
  const { drink, createdAt, content } = review;

  return (
    <Container>
      <Header>
        <Title>{drink?.name}</Title>
        <span>{new Date(createdAt).toLocaleDateString()}</span>
        <EditButton />
      </Header>
      <Content>{content}</Content>
      {/* <ArrowButton size="0.4rem" borderWidth="1.5px" dir="DOWN" /> */}
    </Container>
  );
};

export default TextReview;
