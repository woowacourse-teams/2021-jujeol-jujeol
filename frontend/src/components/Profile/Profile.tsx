import { ImgHTMLAttributes } from 'react';
import { EditIcon } from '../@shared/Icons';
import { Container, EditButton } from './Profile.styles';

interface Props extends ImgHTMLAttributes<HTMLImageElement> {
  ProfileIcon: (props: IconProps) => React.ReactElement;
  nickname?: string;
  bio?: string;
}

const Profile = ({ ProfileIcon, nickname = '', bio = '' }: Props) => {
  const onEditModalOpen = () => {
    console.log('edit Button');
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
