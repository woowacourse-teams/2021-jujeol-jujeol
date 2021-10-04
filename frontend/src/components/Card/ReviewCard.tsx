import { useContext, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { COLOR } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import useShowMoreContent from 'src/hooks/useShowMoreContent';
import Card from '../@shared/Card/Card';
import {
  LoveEmojiColorIcon,
  SmileEmojiColorIcon,
  DizzyEmojiColorIcon,
  ExcitedEmojiColorIcon,
} from '../@shared/Icons';
import EditButton from '../@shared/MeatBallsButton/MeatBallsButton';
import { modalContext } from '../Modal/ModalProvider';
import ReviewEditForm from '../Review/ReviewEditForm';
import { Header, ReviewerInfo, Content, ShowMoreButton } from './ReviewCard.styles';

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
          <EditButton ariaLabel="내 리뷰 글 수정하기 버튼" onClick={onOpenEditForm} />
        )}
      </Header>
      <Content ref={contentRef} isContentOpen={isContentOpen}>
        {content}
      </Content>
      {isShowMore && !isContentOpen && <ShowMoreButton onClick={onOpenContent} />}
    </Card>
  );
};

export default ReviewCard;
