import { css } from '@emotion/react';
import { useRef, useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { COLOR, PATH } from 'src/constants';
import IconButton from '../@shared/Button/IconButton';
import Card from '../@shared/Card/Card';
import ArrowIcon from '../@shared/Icons/ArrowIcon';
import { Img } from '../@shared/Image/Image';
import { TextContainer, Title, Content } from './PersonalReviewCard.styles';

interface Props {
  review: Review.PersonalReviewItem;
}

const PersonalReviewCard = ({ review }: Props) => {
  const { drink } = review;

  const history = useHistory();

  const [isShowMore, setIsShowMore] = useState(false);
  const [isContentOpen, setIsContentOpen] = useState(false);

  const contentRef = useRef<HTMLDivElement>(null);

  const onMoveToDrinkDetail = () => {
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
    <Card width="100%" backgroundColor={COLOR.GRAY_100} padding="0.6rem 1.3rem 0.6rem 0.6rem">
      <Img
        src={drink.imageUrl}
        alt={drink.name}
        shape="ROUND_SQUARE"
        size="SMALL"
        onClick={onMoveToDrinkDetail}
      />

      <TextContainer>
        <div>
          <Title onClick={onMoveToDrinkDetail}>{drink?.name}</Title>
          <span>{new Date(review.createdAt).toLocaleDateString()}</span>
        </div>

        <Content ref={contentRef} isContentOpen={isContentOpen}>
          {review.content}
          {isShowMore && (
            <IconButton
              size="XX_SMALL"
              css={css`
                height: 1rem;

                padding-top: 0;

                position: absolute;
                bottom: 0;
                right: 0;

                background: ${COLOR.GRAY_100};
              `}
              onClick={onShowMore}
            >
              <ArrowIcon color={COLOR.GRAY_500} direction="DOWN" />
            </IconButton>
          )}
        </Content>
      </TextContainer>
    </Card>
  );
};

export default PersonalReviewCard;
