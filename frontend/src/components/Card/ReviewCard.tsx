import { useContext, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';

import { COLOR } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import useShowMoreContent from 'src/hooks/useShowMoreContent';
import {
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
  LoveEmojiColorIcon,
  SmileEmojiColorIcon,
} from '../@Icons';
import ArrowIcon from '../@Icons/ArrowIcon';
import MeatBallsIcon from '../@Icons/MeatBallsIcon';
import IconButton from '../@shared/Button/IconButton';
import Card from '../@shared/Card/Card';
import { modalContext } from '../Modal/ModalProvider';
import ReviewEditForm from '../Review/ReviewEditForm';
import { Content, Header, ReviewerInfo } from './ReviewCard.styles';

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
  const { author, content, createdAt } = review;
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
            aria-label="내 리뷰 글 수정하기 버튼"
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

            padding: 0.3rem;

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
