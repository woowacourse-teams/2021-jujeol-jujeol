import { ChangeEventHandler, FormEventHandler, useContext, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { useHistory, useParams } from 'react-router-dom';

import API from 'src/apis/requests';
import { COLOR, ERROR_MESSAGE, MESSAGE, PATH, REVIEW } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import Card from '../@shared/Card/Card';
import { Form } from './ReviewCreateForm.styles';

const LOGGED_IN_PLACEHOLDER =
  '리뷰를 등록해 주세요. 사용자 별 리뷰는 하루에 1개만 등록할 수 있습니다.';
const NOT_LOGGED_IN_PLACEHOLDER = '리뷰를 등록하기 위해서는 로그인이 필요합니다.';

const ReviewCreateForm = () => {
  const [content, setContent] = useState('');
  const history = useHistory();
  const { id: drinkId } = useParams<{ id: string }>();

  const queryClient = useQueryClient();
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  const { mutate } = useMutation(
    () => API.postReview<Review.PostRequestData>({ drinkId: Number(drinkId), content }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('reviews');
        setContent('');
      },
      onError: (error: { code: number; message: string }) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  const onContentChange: ChangeEventHandler<HTMLTextAreaElement> = ({ target }) => {
    setContent(target.value);
  };

  const onCreate: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    if (!content) return;

    mutate();
  };

  const onCheckLoggedIn = () => {
    if (!isLoggedIn) {
      if (confirm(MESSAGE.LOGIN_REQUIRED_TO_CREATE_REVIEW)) {
        history.push(PATH.LOGIN);
      }
    }
  };

  return (
    <Form onSubmit={onCreate}>
      <Card padding="1rem" backgroundColor={COLOR.GRAY_100}>
        <textarea
          title="리뷰 작성"
          placeholder={isLoggedIn ? LOGGED_IN_PLACEHOLDER : NOT_LOGGED_IN_PLACEHOLDER}
          value={content}
          readOnly={!isLoggedIn}
          maxLength={REVIEW.MAX_LENGTH}
          onClick={onCheckLoggedIn}
          onChange={onContentChange}
          required
        />
        <p>{`${content.length}/${REVIEW.MAX_LENGTH}`}</p>
      </Card>
      <button disabled={!content}>작성 완료</button>
    </Form>
  );
};

export default ReviewCreateForm;
