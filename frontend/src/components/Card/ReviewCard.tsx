import { useContext, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { COLOR } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import useShowMoreContent from 'src/hooks/useShowMoreContent';
import { css } from '@emotion/react';

import Card from '../@shared/Card/Card';
import {
  LoveEmojiColorIcon,
  SmileEmojiColorIcon,
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
} from '../@shared/Icons';
import IconButton from '../@shared/Button/IconButton';
import ArrowIcon from '../@shared/Icons/ArrowIcon';
import MeatBallsIcon from '../@shared/Icons/MeatBallsIcon';
import { modalContext } from '../Modal/ModalProvider';
import ReviewEditForm from '../Review/ReviewEditForm';
import { Header, ReviewerInfo, Content } from './ReviewCard.styles';

interface Props {
  review: Review.Item;
}

const userProfileIcons = [
  SmileEmojiColorIcon,
  LoveEmojiColorIcon,
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
];

const ReviewCard = ({ review }: Props) => {
  const { author, content, createdAt, modifiedAt } = review;
  const { id: drinkId } = useParams<{ id: string }>();

  const openModal = useContext(modalContext)?.openModal;
  const { userData } = useContext(UserContext);

  const contentRef = useRef<HTMLParagraphElement>(null);
  const { isShowMore, isContentOpen, onOpenContent } = useShowMoreContent(contentRef, content);

  const UserProfileIcon = userProfileIcons[author.id % 4];

  const onOpenEditForm = () => {
    openModal?.(<ReviewEditForm drinkId={drinkId} review={review} />);
  };

  return (
    <Card padding="1rem" backgroundColor={COLOR.GRAY_100}>
      <Header>
        <UserProfileIcon />
        <ReviewerInfo>
          <span>{author.name}</span>
          <time>{new Date(createdAt)?.toLocaleDateString()}</time>
        </ReviewerInfo>
        {userData?.id === author.id && (
          <IconButton
            type="button"
            size="SMALL"
            backgroundColor="transparent"
            margin="0 0 0 auto"
            onClick={onOpenEditForm}
          >
            <MeatBallsIcon color={COLOR.GRAY_500} />
          </IconButton>
        )}
      </Header>
      <Content ref={contentRef} isContentOpen={isContentOpen}>
        {content}
      </Content>
      {isShowMore && !isContentOpen && (
        <IconButton
          size="XX_SMALL"
          css={css`
            height: 1rem;

            padding-top: 0;

            position: absolute;
            bottom: 1rem;
            right: 1rem;

            background: ${COLOR.GRAY_100};
          `}
          onClick={onOpenContent}
        >
          <ArrowIcon color={COLOR.GRAY_500} direction="DOWN" />
        </IconButton>
      )}
    </Card>
  );
};

export default ReviewCard;
