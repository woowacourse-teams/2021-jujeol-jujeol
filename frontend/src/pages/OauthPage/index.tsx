import { useContext, useEffect } from 'react';
import { useMutation } from 'react-query';
import { Redirect, useHistory, useLocation } from 'react-router-dom';

import API from 'src/apis/requests';
import { LoveEmojiColorIcon } from 'src/components/@Icons';
import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import { SnackbarContext } from 'src/components/@shared/Snackbar/SnackbarProvider';
import {
  APPLICATION_ERROR_CODE,
  ERROR_MESSAGE,
  LOCAL_STORAGE_KEY,
  MESSAGE,
  PATH,
} from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import { setLocalStorageItem } from 'src/utils/localStorage';
import { Container } from './styles';

const KAKAO_CODE_QUERY_SELECTOR = 'code';

interface RequestData {
  providerName: 'KAKAO';
  code: string;
}

const OauthPage = () => {
  const history = useHistory();
  const location = useLocation();

  const { getUser, isLoggedIn } = useContext(UserContext);
  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const code = new URLSearchParams(location.search).get(KAKAO_CODE_QUERY_SELECTOR);

  let loginFailTimeoutID: NodeJS.Timeout;

  const { mutate } = useMutation(
    () => API.login<RequestData>({ providerName: 'KAKAO', code: code as string }),
    {
      onSuccess: ({ data: { accessToken } }) => {
        clearTimeout(loginFailTimeoutID);

        if (!accessToken) {
          history.push(PATH.LOGIN);
          return;
        }

        setLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, accessToken);
        setSnackbarMessage?.({ type: 'CONFIRM', message: MESSAGE.LOGIN_SUCCESS });
        getUser();
        history.push(PATH.HOME);
      },
      onError: (error: Request.Error) => {
        if (
          error.code === APPLICATION_ERROR_CODE.NETWORK_ERROR ||
          error.code === APPLICATION_ERROR_CODE.INTERNAL_SERVER_ERROR
        ) {
          history.push({
            pathname: PATH.ERROR_PAGE,
            state: { code: error.code },
          });
        }

        setSnackbarMessage?.({
          type: 'ERROR',
          message: ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT,
        });

        loginFailTimeoutID = setTimeout(() => {
          history.push(PATH.LOGIN);
        }, 3000);
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

  if (isLoggedIn) {
    alert(MESSAGE.PAGE_ACCESS_NOT_ALLOWED);
    return <Redirect to={PATH.HOME} />;
  }

  return (
    <Container>
      <FlexBox flexDirection="column" justifyContent="center" alignItems="center">
        <LoveEmojiColorIcon />
        <p>로그인 하는 중</p>
      </FlexBox>
    </Container>
  );
};

export default OauthPage;
