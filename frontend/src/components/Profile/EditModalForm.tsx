import { ChangeEvent, FormEventHandler, useContext, useEffect, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { useHistory } from 'react-router-dom';
import { css } from '@emotion/react';

import API from 'src/apis/requests';
import { APPLICATION_ERROR_CODE, COLOR, ERROR_MESSAGE, MESSAGE, PATH } from 'src/constants';
import QUERY_KEY from 'src/constants/queryKey';
import Button from '../@shared/Button/Button';
import Heading from '../@shared/Heading/Heading';
import { SnackbarContext } from '../@shared/Snackbar/SnackbarProvider';
import { modalContext } from '../Modal/ModalProvider';
import { BioInput, Form, NicknameInput } from './EditModalForm.styles';

interface Props {
  nickname?: string;
  bio?: string;
}

const EditModalForm = ({ nickname: currentNickname = '', bio: currentBio = '' }: Props) => {
  const history = useHistory();

  const { isModalOpened, closeModal } = useContext(modalContext) ?? {};
  const snackbar = useContext(SnackbarContext);

  const queryClient = useQueryClient();

  const [nickname, setNickname] = useState(currentNickname);
  const [bio, setBio] = useState(currentBio);

  useEffect(() => {
    if (!isModalOpened) {
      setNickname('');
      setBio('');
      return;
    }

    setNickname(currentNickname);
    setBio(currentBio);
  }, [isModalOpened]);

  const { mutate: editUserInfo } = useMutation(
    () =>
      API.editUserInfo<{ nickname: string; bio: string }>({
        nickname,
        bio,
      }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(QUERY_KEY.USER);
        closeModal?.();
        snackbar?.setSnackbarMessage({ type: 'CONFIRM', message: MESSAGE.EDIT_PROFILE_SUCCESS });
      },
      onError: (error: Request.Error) => {
        if (
          error.code === APPLICATION_ERROR_CODE.NETWORK_ERROR ||
          error.code === APPLICATION_ERROR_CODE.INTERNAL_SERVER_ERROR
        ) {
          history.push({
            pathname: PATH.ERROR_PAGE,
            state: { code: error.code },
          });
        }

        snackbar?.setSnackbarMessage({
          type: 'ERROR',
          message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT,
        });
      },
    }
  );

  const onEditProfile: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    if (nickname === currentNickname && bio === currentBio) {
      closeModal?.();
      return;
    }

    editUserInfo();
  };

  const onEditNickname = ({ target: { value } }: ChangeEvent<HTMLInputElement>) => {
    setNickname(value);
  };

  const onEditBio = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setBio(event.target.value);
  };

  return (
    <Form onSubmit={onEditProfile}>
      <Heading.level2
        color={COLOR.BLACK}
        css={css`
          text-align: center;
        `}
      >
        닉네임 수정하기
      </Heading.level2>

      <label>
        <Heading.level3 color={COLOR.BLACK}>닉네임</Heading.level3>
        <NicknameInput
          value={nickname}
          onChange={onEditNickname}
          required
          maxLength={10}
          placeholder="닉네임을 입력해주세요"
        />
        <p>{ERROR_MESSAGE[1007]}</p>
      </label>

      <label>
        <Heading.level3 color={COLOR.BLACK}>소개</Heading.level3>
        <BioInput
          value={bio}
          onChange={onEditBio}
          required
          maxLength={35}
          placeholder="소개를 통해 자신을 뽐내주세요"
        />
      </label>

      <Button disabled={!nickname || !bio}>수정하기</Button>
    </Form>
  );
};

export default EditModalForm;
