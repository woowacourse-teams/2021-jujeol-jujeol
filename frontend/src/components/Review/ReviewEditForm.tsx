import { FormEventHandler, useContext, useEffect, useRef, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import API from 'src/apis/requests';
import { COLOR, ERROR_MESSAGE, MESSAGE, REVIEW } from 'src/constants';
import Button from '../@shared/Button/Button';
import TextButton from '../@shared/Button/TextButton';
import Heading from '../@shared/Heading/Heading';
import { confirmContext } from '../Confirm/ConfirmProvider';
import { modalContext } from '../Modal/ModalProvider';
import { Content, Form } from './ReviewEditForm.styles';

interface Props {
  drinkId: string;
  review: Review.Item;
}

const ReviewEditForm = ({ drinkId, review }: Props) => {
  const { id: reviewId, content, createdAt } = review;

  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const { isModalOpened, closeModal } = useContext(modalContext) ?? {};
  const { setConfirm, closeConfirm } = useContext(confirmContext) ?? {};

  const [editContent, setEditContent] = useState(content);

  const queryClient = useQueryClient();

  const { mutate: deleteReview } = useMutation(() => API.deleteReview<number>(reviewId), {
    onSuccess: () => {
      queryClient.invalidateQueries('reviews');
      closeModal?.();
      closeConfirm?.();
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
    setConfirm?.({
      message: MESSAGE.CONFIRM_DELETE_REVIEW,
      subMessage: MESSAGE.CANNOT_REDO,
      onConfirm: deleteReview,
      onCancel: closeConfirm as () => void,
      direction: 'reverse',
    });
  };

  return (
    <>
      <Form onSubmit={onEdit}>
        <Heading.level2 color={COLOR.BLACK}>리뷰 수정하기</Heading.level2>
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

        <Button disabled={!editContent}>수정하기</Button>
      </Form>
      <TextButton type="button" onClick={onDelete} color={COLOR.GRAY_300} margin="0.2rem 0 0 auto">
        삭제하기
      </TextButton>
    </>
  );
};

export default ReviewEditForm;
