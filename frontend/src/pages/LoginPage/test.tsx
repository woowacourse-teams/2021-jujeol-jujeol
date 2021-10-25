import { fireEvent, screen } from '@testing-library/react';

import { PATH } from 'src/constants';
import { customRender } from 'src/tests/customRenderer';
import LoginPage from '.';

import '@testing-library/jest-dom';

describe('사용자는 로그인 페이지에서 소셜 로그인을 할 수 있다.', () => {
  beforeAll(async () => {
    customRender({ initialEntries: [PATH.LOGIN], children: <LoginPage /> });
  });

  it('사용자는 로그인 페이지에서 카카오톡 로그인 버튼을 클릭할 수 있다.', async () => {
    const loginButton = screen.getByText('카카오 로그인').closest('a') as Element;

    expect(loginButton).toHaveAttribute('href');
    expect(loginButton).toBeEnabled();
    fireEvent.click(loginButton);
  });
});
