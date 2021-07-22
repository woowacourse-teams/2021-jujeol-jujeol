import { ImgHTMLAttributes } from 'react';
import Image from '../@shared/Image/Image';
import { Container } from './Profile.styles';

interface Props extends ImgHTMLAttributes<HTMLImageElement> {
  nickname: string;
  introduction: string;
}

const Profile = ({ src, nickname, introduction }: Props) => {
  return (
    <Container>
      <Image src={src} alt="프로필 사진" shape="CIRCLE" size="SMALL" />

      <div>
        <h3>{nickname}</h3>
        <p>{introduction}</p>
      </div>
    </Container>
  );
};

export default Profile;
