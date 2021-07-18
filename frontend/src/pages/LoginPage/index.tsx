import { Logo, Container } from './styles';
import KakaoLogin from 'src/components/SocialLogin/KakaoLogin';
import JujeolLogoSquare from 'src/assets/jujeol_logo_square';
import { useContext } from 'react';
import UserContext from 'src/contexts/UserContext';
import { Redirect } from 'react-router-dom';
import { PATH } from 'src/constants';

const LoginPage = () => {
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  if (isLoggedIn) {
    alert('접근 할 수 없는 페이지 입니다.');
    return <Redirect to={PATH.HOME} />;
  }

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
