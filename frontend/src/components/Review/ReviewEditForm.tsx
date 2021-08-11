import { FormEventHandler, useContext, useEffect, useRef, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import API from 'src/apis/requests';
import { ERROR_MESSAGE, REVIEW } from 'src/constants';
import { modalContext } from '../Modal/ModalProvider';
import { Form, Content, EditButton, DeleteButton } from './ReviewEditForm.styles';
interface Props {
  drinkId: string;
  review: Review.Item;
}

const ReviewEditForm = ({ drinkId, review }: Props) => {
  const { id: reviewId, content, createdAt, modifiedAt } = review;

  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const { isModalOpened, closeModal } = useContext(modalContext) ?? {};

  const [editContent, setEditContent] = useState(content);

  const queryClient = useQueryClient();

  const { mutate: deleteReview } = useMutation(() => API.deleteReview<number>(reviewId), {
    onSuccess: () => {
      queryClient.invalidateQueries('reviews');
      closeModal?.();
    },
    onError: () => {
      alert(ERROR_MESSAGE.DEFAULT);
    },
  });

  const { mutate: editReview } = useMutation(
    () =>
      API.editReview<Review.PostRequestData>(reviewId, {
        drinkId: Number(drinkId),
        content: editContent,
      }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('reviews');
        closeModal?.();
      },
      onError: () => {
        alert(ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  useEffect(() => {
    if (!isModalOpened) {
      setEditContent('');
      return;
    }

    setEditContent(content);

    if (textAreaRef.current) {
      textAreaRef.current.focus();
      textAreaRef.current.setSelectionRange(content.length, content.length);
    }
  }, [isModalOpened]);

  const onEdit: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    if (editContent === content) {
      closeModal?.();
      return;
    }

    editReview();
  };

  const onDelete = () => {
    if (confirm('리뷰를 삭제하시겠습니까?')) {
      deleteReview();
    }
  };

  return (
    <>
      <Form onSubmit={onEdit}>
        <h2>리뷰 수정하기</h2>
        <Content>
          <div>
            <span>{new Date(createdAt).toLocaleDateString()}</span>
            <span>{`${editContent.length}/${REVIEW.MAX_LENGTH}`}</span>
          </div>
          <textarea
            value={editContent}
            onChange={({ target }) => setEditContent(target.value)}
            ref={textAreaRef}
            placeholder="리뷰를 작성해 주세요"
            maxLength={REVIEW.MAX_LENGTH}
            required
          />
        </Content>

        <EditButton disabled={!editContent}>수정하기</EditButton>
      </Form>
      <DeleteButton type="button" onClick={onDelete}>
        삭제하기
      </DeleteButton>
    </>
  );
};

export default ReviewEditForm;
