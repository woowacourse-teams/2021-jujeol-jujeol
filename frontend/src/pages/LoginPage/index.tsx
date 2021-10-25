import { useContext } from 'react';
import { Redirect } from 'react-router-dom';

import JujeolLogoSquare from 'src/assets/jujeol_logo_square';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import KakaoLogin from 'src/components/SocialLogin/KakaoLogin';
import { MESSAGE, PATH } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import usePageTitle from 'src/hooks/usePageTitle';
import { Container, Logo } from './styles';

const LoginPage = () => {
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;
  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  usePageTitle('로그인');

  if (isLoggedIn) {
    setSnackbarMessage?.({ type: 'ERROR', message: MESSAGE.PAGE_ACCESS_NOT_ALLOWED });
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
