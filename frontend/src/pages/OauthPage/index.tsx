import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { Redirect, useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import { PATH } from 'src/constants';

const KAKAO_CODE_QUERY_SELECTOR = 'code';

interface RequestData {
  providerName: 'KAKAO';
  code: string;
}

const OauthPage = () => {
  const history = useHistory();
  const code = new URLSearchParams(location.search).get(KAKAO_CODE_QUERY_SELECTOR);

  const { mutate } = useMutation(
    () => API.login<RequestData>({ providerName: 'KAKAO', code: code as string }),
    {
      onSuccess: ({ data: { accessToken } }) => {
        if (!accessToken) {
          alert('서버 오류가 발생했습니다. 다시 한 번 시도해 주세요');
          history.push(PATH.LOGIN);
          return;
        }
        localStorage.setItem('jujeol_access_token', accessToken);
        history.push(PATH.HOME);
      },
      onError: () => {
        alert('서버 오류가 발생했습니다. 다시 한 번 시도해 주세요');
      },
    }
  );

  useEffect(() => {
    if (code) {
      mutate();
    }
  }, [code]);

  if (!code) {
    return <Redirect to={PATH.LOGIN} />;
  }

  return <div></div>;
};

export default OauthPage;
