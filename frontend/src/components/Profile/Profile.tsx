import { ImgHTMLAttributes } from 'react';
import { Img } from '../@shared/Image/Image';
import { Container } from './Profile.styles';

interface Props extends ImgHTMLAttributes<HTMLImageElement> {
  nickname: string;
  bio: string;
}

const Profile = ({ src, nickname, bio }: Props) => {
  return (
    <Container>
      <Img src={src} alt="프로필 사진" shape="CIRCLE" size="SMALL" />

      <div>
        <h3>{nickname}</h3>
        <p>{bio}</p>
      </div>
    </Container>
  );
};

export default Profile;
