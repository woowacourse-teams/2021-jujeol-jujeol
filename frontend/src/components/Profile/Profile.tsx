import { ImgHTMLAttributes, useContext } from 'react';

import { hiddenStyle } from 'src/styles/hidden';
import { EditIcon } from '../@Icons';
import IconButton from '../@shared/Button/IconButton';
import Heading from '../@shared/Heading/Heading';
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
        <Heading.level3 css={hiddenStyle}>프로필</Heading.level3>
        <span>{nickname}</span>
        <IconButton
          aria-label="프로필 수정"
          type="button"
          size="X_SMALL"
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
