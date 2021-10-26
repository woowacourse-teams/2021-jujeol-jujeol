import { ACCESS_TOKEN_KEY } from '../constants';

const checkUserValidation = () => {
  const accessToken = localStorage.getItem(ACCESS_TOKEN_KEY);

  if (!accessToken) {
    return false;
  }

  // login되어있는지 체크하는 req

  try {
    // const response = await fetch(API_URL + '/login/token', {
    //   method: 'POST',
    // });
    // if (!response.ok) {
    //   throw new Error(await response.text());
    // }
    // return await response.json();
  } catch (error) {
    // 로그인 x 상태, 무조건 login 화면으로 보냄
    history.push('/pages/login');
  }
};

export default checkUserValidation;
