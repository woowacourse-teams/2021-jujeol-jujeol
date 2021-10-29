import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import { Img } from '../@shared/Image/Image';
import { Content, TextContainer, Title } from './PersonalReviewCard.styles';

interface Props {
  review: Review.PersonalReviewItem;
}

const PersonalReviewCard = ({ review }: Props) => {
  const { drink } = review;

  return (
    <Card
      width="100%"
      height="100%"
      backgroundColor={COLOR.GRAY_100}
      padding="0.6rem 1.3rem 0.6rem 0.6rem"
    >
      <Img src={drink.imageUrl} alt={drink.name} shape="ROUND_SQUARE" size="SMALL" />

      <TextContainer>
        <div>
          <Title>{drink?.name}</Title>
          <span>{new Date(review.createdAt).toLocaleDateString()}</span>
        </div>

        <Content>{review.content}</Content>
      </TextContainer>
    </Card>
  );
};

export default PersonalReviewCard;
