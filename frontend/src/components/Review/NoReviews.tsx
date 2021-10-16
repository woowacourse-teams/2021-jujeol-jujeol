import { DizzyEmojiColorIcon } from '../@Icons';
import { Container } from './NoReviews.styles';

const NoReviews = ({ drinkName }: { drinkName: string }) => {
  return (
    <Container>
      <DizzyEmojiColorIcon />
      <p>
        <span>{drinkName}</span>에 대한 리뷰가 없어요.
      </p>
      <p>첫 리뷰의 주인공이 되어보세요!</p>
    </Container>
  );
};

export default NoReviews;
