import React, { useEffect, useRef, useState } from 'react';
import { COLOR } from 'src/constants';
import HumanIcon from '../Icons/human';
import Card from './Card';
import { Header, ReviewerInfo, Content, ShowMoreButton } from './ReviewCard.styles';

interface ReviewData {
  id: number;
  author: {
    id: number;
    name: string;
  };
  content: string;
  createdAt: Date;
  modifiedAt: Date | null;
}

interface Props {
  review: ReviewData;
}

const ReviewCard = ({ review }: Props) => {
  const { id, author, content, createdAt, modifiedAt } = review;

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
          <span>{author.name}</span>
          <time>{createdAt.toLocaleDateString()}</time>
        </ReviewerInfo>
      </Header>
      <Content ref={contentRef} isContentOpen={isContentOpen}>
        {content}
      </Content>
      {isShowMore && <ShowMoreButton onClick={onShowMore} />}
    </Card>
  );
};

export default ReviewCard;
