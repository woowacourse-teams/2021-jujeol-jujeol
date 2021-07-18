import { useEffect, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import API from 'src/apis/requests';
import { ERROR_MESSAGE, REVIEW } from 'src/constants';
import { Form, Content, EditButton, DeleteButton } from './ReviewEditForm.styles';
interface Props {
  drinkId: string;
  review: Review.ReviewItem;
}

const ReviewEditForm = ({ drinkId, review }: Props) => {
  const { id: reviewId, content, createdAt, modifiedAt } = review;

  const [editText, setEditText] = useState(content);
  const queryClient = useQueryClient();

  const { mutate: deleteReview } = useMutation(
    () => API.deleteReview<string>(drinkId, reviewId.toString()),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('reviews');
      },
      onError: () => {
        alert(ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  useEffect(() => {
    setEditText(review.content);
  }, [review]);

  const onDelete = () => {
    if (confirm('리뷰를 삭제하시겠습니까?')) {
      deleteReview();
    }
  };

  return (
    <Form>
      <h2>리뷰 수정하기</h2>
      <Content>
        <div>
          <span>{new Date(createdAt).toLocaleDateString()}</span>
          <span>{`${editText.length}/${REVIEW.MAX_LENGTH}`}</span>
        </div>
        <textarea value={editText} onChange={({ target }) => setEditText(target.value)} />
      </Content>

      <EditButton>수정하기</EditButton>
      <DeleteButton type="button" onClick={onDelete}>
        삭제하기
      </DeleteButton>
    </Form>
  );
};

export default ReviewEditForm;
