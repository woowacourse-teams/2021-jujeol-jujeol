import { ImgHTMLAttributes, useContext } from 'react';
import IconButton from '../@shared/Button/IconButton';

import { EditIcon } from '../@Icons';
import { modalContext } from '../Modal/ModalProvider';
import EditModalForm from './EditModalForm';
import { Container, EditButtonStyle } from './Profile.styles';

interface Props extends ImgHTMLAttributes<HTMLImageElement> {
  ProfileIcon: (props: IconProps) => React.ReactElement;
  nickname?: string;
  bio?: string;
}

const Profile = ({ ProfileIcon, nickname = '', bio = '' }: Props) => {
  const openModal = useContext(modalContext)?.openModal;

  const onEditModalOpen = () => {
    openModal?.(<EditModalForm nickname={nickname} bio={bio} />);
  };

  return (
    <Container>
      <ProfileIcon width="72px" height="72px" />

      <div>
        <h3>{nickname}</h3>
        <IconButton
          aria-label="프로필 수정"
          type="button"
          size="SMALL"
          onClick={onEditModalOpen}
          css={EditButtonStyle}
        >
          <EditIcon />
        </IconButton>
        <p>{bio}</p>
      </div>
    </Container>
  );
};

export default Profile;
