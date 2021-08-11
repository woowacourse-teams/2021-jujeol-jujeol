import { useContext, useEffect } from 'react';
import { useMutation } from 'react-query';
import { Redirect, useHistory, useLocation } from 'react-router-dom';
import UserContext from 'src/contexts/UserContext';
import API from 'src/apis/requests';
import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import { LoveEmojiColorIcon } from 'src/components/@shared/Icons';
import { setLocalStorageItem } from 'src/utils/localStorage';
import { LOCAL_STORAGE_KEY, PATH, MESSAGE } from 'src/constants';
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
        getUser();
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

  if (isLoggedIn) {
    alert(MESSAGE.PAGE_ACCESS_NOT_ALLOWED);
    return <Redirect to={PATH.HOME} />;
  }

  return (
    <Container>
      <FlexBox flexDirection="column" justifyContent="center" alignItems="center" rowGap="2rem">
        <LoveEmojiColorIcon />
        <p>로그인 하는 중</p>
      </FlexBox>
    </Container>
  );
};

export default OauthPage;
