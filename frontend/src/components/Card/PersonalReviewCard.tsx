import { useHistory } from 'react-router-dom';
import { COLOR, PATH } from 'src/constants';
import Card from '../@shared/Card/Card';
import { Img } from '../@shared/Image/Image';
import EditButton from '../EditButton/EditButton';
import { TextContainer, Title, Content } from './PersonalReviewCard.styles';

interface Props {
  review: MyReview.MyReviewItem;
}

const PersonalReviewCard = ({ review }: Props) => {
  const { drink } = review;

  const history = useHistory();

  const onMoveDrinkDetailPage = () => {
    history.push(`${PATH.DRINKS}/${review.drink.drinkId}`);
  };

  return (
    <Card
      width="100%"
      backgroundColor="transparent"
      padding="0.6rem 1.3rem 0.6rem 0.6rem"
      border={`1px solid ${COLOR.PURPLE_100}`}
    >
      <Img
        src={drink.imageUrl}
        alt={drink.name}
        shape="ROUND_SQUARE"
        size="SMALL"
        onClick={onMoveDrinkDetailPage}
      />
      <TextContainer>
        <div>
          <Title onClick={onMoveDrinkDetailPage}>{drink?.name}</Title>
          <span>{new Date(review.createdAt).toLocaleDateString()}</span>
          <EditButton />
        </div>
        <Content>{review.content}</Content>
        {/* <ArrowButton size="0.4rem" borderWidth="1.5px" dir="DOWN" /> */}
      </TextContainer>
    </Card>
  );
};

export default PersonalReviewCard;
