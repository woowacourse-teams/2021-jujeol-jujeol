import '@testing-library/jest-dom';
import { waitFor } from '@testing-library/react';
import { Route, Switch } from 'react-router-dom';
import { Location } from 'history';
import { customRender } from 'src/tests/customRenderer';
import API from 'src/apis/requests';

import OauthPage from '.';

const code = 'afadd123fiwelrjkaaaaafjfghkddltrkl34jk';
const accessToken = 'afadd123fiwelrjkaaaaafjfghkddltrkl34jk';

describe('OauthPage 테스트', () => {
  let testLocation: Location<unknown>;

  beforeAll(() => {
    API.login = jest.fn().mockReturnValue({ data: { accessToken } });
  });

  it('코드를 이용해 서버로 부터 accessToken을 받아올 수 있다.', async () => {
    customRender({ initialEntries: [`/oauth?code=${code}`], children: <OauthPage /> });

    await waitFor(() => expect(API.login).toBeCalledTimes(1));
  });

  it('[예외] 코드가 서버로 부터 오지 않으면, 로그인 페이지로 돌아간다.', async () => {
    const children = (
      <>
        <Switch>
          <Route exact path="/oauth">
            <OauthPage />
          </Route>
          <Route exact path="/login">
            login
          </Route>
        </Switch>
        <Route
          path="*"
          render={({ location }) => {
            testLocation = location;
            return null;
          }}
        />
      </>
    );

    customRender({ initialEntries: [`/oauth`], children });

    expect(testLocation.pathname).toBe('/login');
  });
});
