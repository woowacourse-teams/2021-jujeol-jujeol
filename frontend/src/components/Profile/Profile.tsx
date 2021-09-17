import { ImgHTMLAttributes, useContext } from 'react';

import { EditIcon } from '../@shared/Icons';
import { modalContext } from '../Modal/ModalProvider';
import EditModalForm from './EditModalForm';
import { Container, EditButton } from './Profile.styles';

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
        <EditButton onClick={onEditModalOpen}>
          <EditIcon width="1.5rem" height="1.5rem" />
        </EditButton>
        <p>{bio}</p>
      </div>
    </Container>
  );
};

export default Profile;
