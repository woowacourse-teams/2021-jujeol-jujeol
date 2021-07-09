import { PATH } from 'src/constants';
import KakaoIcon from '../Icons/symbol_kakao';
import { KakaoLoginButton } from './KakaoLogin.styles';

const HOST = 'https://kauth.kakao.com/oauth/authorize';
const CID = '838bcd43c3b007b4ef38c3da555c4806';
const REDIRECT_URL = `http://localhost:8080${PATH.OAUTH}`;

const requestUri = `${HOST}?client_id=${CID}&redirect_uri=${REDIRECT_URL}&response_type=code`;

const KakaoLogin = () => {
  return (
    <a href={requestUri}>
      <KakaoLoginButton>
        <KakaoIcon />
        <span>카카오 로그인</span>
      </KakaoLoginButton>
    </a>
  );
};

export default KakaoLogin;
