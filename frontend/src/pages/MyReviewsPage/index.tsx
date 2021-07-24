import { useHistory } from 'react-router-dom';
import ArrowButton from 'src/components/@shared/ArrowButton/ArrowButton';
import MyReviewItem from './MyReviewItem';
import { Container, Header } from './styles';

const MyReviewsPage = () => {
  const history = useHistory();

  return (
    <>
      <Header>
        <ArrowButton size="0.7rem" borderSize="2px" dir="LEFT" onClick={() => history.goBack()} />
        <h2>내가 남긴 리뷰</h2>
      </Header>

      <Container>
        {Array.from({ length: 12 }).map((_, index) => (
          <MyReviewItem key={index} />
        ))}
      </Container>
    </>
  );
};

export default MyReviewsPage;
