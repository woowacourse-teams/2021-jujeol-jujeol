import { ImgHTMLAttributes, useContext } from 'react';
import IconButton from '../@shared/Button/IconButton';

import { EditIcon } from '../@Icons';
import { modalContext } from '../Modal/ModalProvider';
import EditModalForm from './EditModalForm';
import { Container, EditButtonStyle } from './Profile.styles';
import Heading from '../@shared/Heading/Heading';
import { css } from '@emotion/react';
import { hiddenStyle } from 'src/styles/hidden';

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
