import { Redirect } from 'react-router-dom';
import { PATH } from 'src/constants';

const KAKAO_CODE_QUERY_SELECTOR = 'code';

const OauthPage = () => {
  const code = new URLSearchParams(location.search).get(KAKAO_CODE_QUERY_SELECTOR);

  if (!code) {
    return <Redirect to={PATH.LOGIN} />;
  }

  return <div>oauth</div>;
};

export default OauthPage;
