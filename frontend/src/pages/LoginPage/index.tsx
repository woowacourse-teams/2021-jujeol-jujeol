import KakaoIcon from 'src/components/Icons/symbol_kakao';
import { Logo, Container, KakaoLoginButton } from './styles';
import logo from 'src/assets/jujeol_logo_square.svg';

const LoginPage = () => {
  return (
    <Container>
      <Logo>
        <img src={logo} alt="주절주절 로고" width="180px" />
      </Logo>

      <div>
        <p>만 18세 이상만 이용 가능한 서비스입니다.</p>
        <KakaoLoginButton>
          <KakaoIcon />
          <span>카카오 로그인</span>
        </KakaoLoginButton>
      </div>
    </Container>
  );
};

export default LoginPage;
