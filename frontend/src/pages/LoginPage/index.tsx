import { Logo, Container } from './styles';
import KakaoLogin from 'src/components/SocialLogin/KakaoLogin';
import JujeolLogoSquare from 'src/assets/jujeol_logo_square';

const LoginPage = () => {
  return (
    <Container>
      <Logo>
        <JujeolLogoSquare />
      </Logo>

      <div>
        <p>만 18세 이상만 이용 가능한 서비스입니다.</p>
        <KakaoLogin />
      </div>
    </Container>
  );
};

export default LoginPage;
