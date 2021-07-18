import { useEffect, useState } from 'react';
import { Form, Content, EditButton, DeleteButton } from './ReviewEditForm.styles';
interface Props {
  review: Review.ReviewItem;
}

const ReviewEditForm = ({ review }: Props) => {
  const { id, author, content, createdAt, modifiedAt } = review;

  const [editText, setEditText] = useState(content);

  useEffect(() => {
    setEditText(review.content);
  }, [review]);

  return (
    <Form>
      <h2>리뷰 수정하기</h2>
      <Content>
        <div>
          <span>21.07.12</span>
          <span>0/300</span>
        </div>
        <textarea value={editText} onChange={({ target }) => setEditText(target.value)} />
      </Content>

      <EditButton>수정하기</EditButton>
      <DeleteButton type="button">삭제하기</DeleteButton>
    </Form>
  );
};

export default ReviewEditForm;
