import { ImgHTMLAttributes } from 'react';
import { Img } from '../@shared/Image/Image';
import { Container } from './Profile.styles';

interface Props extends ImgHTMLAttributes<HTMLImageElement> {
  ProfileIcon: (props: IconProps) => React.ReactElement;
  nickname?: string;
  bio?: string;
}

const Profile = ({ ProfileIcon, nickname = '', bio = '' }: Props) => {
  return (
    <Container>
      <ProfileIcon />

      <div>
        <h3>{nickname}</h3>
        <p>{bio}</p>
      </div>
    </Container>
  );
};

export default Profile;
