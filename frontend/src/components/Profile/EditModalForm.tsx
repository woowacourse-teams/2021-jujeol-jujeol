import { ChangeEvent, FormEventHandler, useContext, useEffect, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';

import Button from '../@shared/Button/Button';
import API from 'src/apis/requests';
import { modalContext } from '../Modal/ModalProvider';
import { SnackbarContext } from '../@shared/Snackbar/SnackbarProvider';
import { BioInput, Form, NicknameInput } from './EditModalForm.styles';

interface Props {
  nickname?: string;
  bio?: string;
}

const EditModalForm = ({ nickname: currentNickname = '', bio: currentBio = '' }: Props) => {
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
        queryClient.invalidateQueries('user-info');
        closeModal?.();
      },
      onError: (error: { code: number; message: string }) => {
        snackbar?.setSnackbarMessage({ type: 'ERROR', message: error.message });
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
      <h2>닉네임 수정하기</h2>

      <label>
        <h3>닉네임</h3>
        <NicknameInput
          value={nickname}
          onChange={onEditNickname}
          required
          maxLength={10}
          placeholder="닉네임을 입력해주세요"
        />
      </label>

      <label>
        <h3>소개</h3>
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
