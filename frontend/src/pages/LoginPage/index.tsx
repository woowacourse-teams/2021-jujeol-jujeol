import { Logo, Container } from './styles';
import KakaoLogin from 'src/components/SocialLogin/KakaoLogin';
import JujeolLogoSquare from 'src/assets/jujeol_logo_square';
import { useContext } from 'react';
import UserContext from 'src/contexts/UserContext';
import { Redirect } from 'react-router-dom';
import { MESSAGE, PATH } from 'src/constants';
import usePageTitle from 'src/hooks/usePageTitle';

const LoginPage = () => {
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  usePageTitle('로그인');

  if (isLoggedIn) {
    alert(MESSAGE.PAGE_ACCESS_NOT_ALLOWED);
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
