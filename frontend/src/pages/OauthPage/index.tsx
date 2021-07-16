import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { Redirect, useHistory, useLocation } from 'react-router-dom';
import API from 'src/apis/requests';
import { LOCAL_STORAGE_KEY, PATH } from 'src/constants';
import { setLocalStorageItem } from 'src/utils/localStorage';

const KAKAO_CODE_QUERY_SELECTOR = 'code';

interface RequestData {
  providerName: 'KAKAO';
  code: string;
}

const OauthPage = () => {
  const history = useHistory();
  const location = useLocation();

  const code = new URLSearchParams(location.search).get(KAKAO_CODE_QUERY_SELECTOR);

  const { mutate } = useMutation(
    () => API.login<RequestData>({ providerName: 'KAKAO', code: code as string }),
    {
      onSuccess: ({ data: { accessToken } }) => {
        if (!accessToken) {
          history.push(PATH.LOGIN);
          return;
        }

        setLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, accessToken);
        history.push(PATH.HOME);
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

  return <></>;
};

export default OauthPage;
