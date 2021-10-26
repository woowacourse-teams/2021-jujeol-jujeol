const { ACCESS_TOKEN_KEY } = require('./constants');

const $loginForm = document.querySelector('#login-form');
const $loginAlert = document.querySelector('#login-alert');

const API_URL = process.env.API_URL;

$loginForm?.addEventListener('submit', async (event) => {
  event?.preventDefault();

  const id = event.target['id'].value;
  const password = event.target['password'].value;

  try {
    const response = await fetch(API_URL + '/admin/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ id, password }),
    });

    if (!response.ok) {
      throw new Error(await response.text());
    }

    const {
      data: { accessToken },
    } = await response.json();

    localStorage.setItem(ACCESS_TOKEN_KEY, JSON.stringify(accessToken));

    location.href = '/index.html';
  } catch (error) {
    console.error(error);

    $loginAlert.textContent = '로그인 할 수 없습니다. 아이디, 비밀번호를 확인하세요.';
  }
});
