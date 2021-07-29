import { useRef, useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { COLOR, PATH } from 'src/constants';
import Arrow from '../@shared/Arrow/Arrow';
import Card from '../@shared/Card/Card';
import { Img } from '../@shared/Image/Image';
import EditButton from '../EditButton/EditButton';
import { TextContainer, Title, Content, ShowMoreButton } from './PersonalReviewCard.styles';

interface Props {
  review: PersonalReview.PersonalReviewItem;
}

const PersonalReviewCard = ({ review }: Props) => {
  const { drink } = review;

  const history = useHistory();

  const [isShowMore, setIsShowMore] = useState(false);
  const [isContentOpen, setIsContentOpen] = useState(false);

  const contentRef = useRef<HTMLDivElement>(null);

  const onMoveDrinkDetailPage = () => {
    history.push(`${PATH.DRINKS}/${review.drink.drinkId}`);
  };

  useEffect(() => {
    const content = contentRef.current;
    if (content) {
      setIsShowMore(content.clientHeight < content.scrollHeight);
    }
  }, [contentRef, review.content]);

  const onShowMore = () => {
    setIsShowMore(false);
    setIsContentOpen(true);
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

        <Content ref={contentRef} isContentOpen={isContentOpen}>
          {review.content}
        </Content>
        {isShowMore && (
          <ShowMoreButton onClick={onShowMore}>
            <Arrow size="0.4rem" borderWidth="1.5px" dir="DOWN" />
          </ShowMoreButton>
        )}
      </TextContainer>
    </Card>
  );
};

export default PersonalReviewCard;
