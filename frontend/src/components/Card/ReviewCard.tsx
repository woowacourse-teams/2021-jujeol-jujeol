import { useContext, useEffect, useRef, useState } from 'react';
import { COLOR } from 'src/constants';
import nicknameGenerator from 'src/utils/createNickname';
import HumanIcon from '../Icons/human';
import { modalContext } from '../Modal/ModalProvider';
import ReviewEditForm from '../Review/ReviewEditForm';
import Card from './Card';
import { Header, ReviewerInfo, Content, ShowMoreButton } from './ReviewCard.styles';

interface Props {
  review: Review.ReviewItem;
}

const ReviewCard = ({ review }: Props) => {
  const { id, author, content, createdAt, modifiedAt } = review;

  const openModal = useContext(modalContext)?.openModal;

  const contentRef = useRef<HTMLDivElement>(null);
  const [isShowMore, setIsShowMore] = useState(false);
  const [isContentOpen, setIsContentOpen] = useState(false);

  useEffect(() => {
    const content = contentRef.current;
    if (content) {
      setIsShowMore(content.clientHeight < content.scrollHeight);
    }
  }, [contentRef]);

  const onShowMore = () => {
    setIsShowMore(false);
    setIsContentOpen(true);
  };

  return (
    <Card padding="1rem" backgroundColor={COLOR.WHITE_200}>
      <Header>
        <HumanIcon color={COLOR.YELLOW_300} />
        <ReviewerInfo>
          <span>{nicknameGenerator(author.id.toString())}</span>
          <time>{createdAt?.toLocaleDateString()}</time>
        </ReviewerInfo>
        <button
          type="button"
          onClick={() => openModal?.(<ReviewEditForm review={review} />)}
          aria-label="내 리뷰 글 수정하기 버튼"
        >
          <svg width="32px" height="32px" viewBox="0 0 100 100" fill="grey">
            <circle cx="30" cy="50" r="6" />
            <circle cx="50" cy="50" r="6" />
            <circle cx="70" cy="50" r="6" />
          </svg>
        </button>
      </Header>
      <Content ref={contentRef} isContentOpen={isContentOpen}>
        {content}
      </Content>
      {isShowMore && <ShowMoreButton onClick={onShowMore} />}
    </Card>
  );
};

export default ReviewCard;
