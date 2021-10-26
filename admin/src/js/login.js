const $loginForm = document.querySelector('#login-form');
const $loginAlert = document.querySelector('#login-alert');

// const API_URL = process.env.API_URL;

$loginForm.addEventListener('submit', async (event) => {
  event?.preventDefault();

  // 로그인 요청 보내는 req
  try {
    // const response = await fetch(API_URL + '/login', {
    //   method: 'POST',
    // });
    // history.push('/admin');
  } catch (error) {
    // 사용자 입력 에러
    $loginAlert.textContent = '로그인 할 수 없습니다. 아이디, 비밀번호를 확인하세요.';
  }
});
