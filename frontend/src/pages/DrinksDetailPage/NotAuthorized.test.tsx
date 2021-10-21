import '@testing-library/jest-dom';
import { screen, waitFor, fireEvent } from '@testing-library/react';
import { customRender } from 'src/tests/customRenderer';
import { MockIntersectionObserver } from 'src/tests/mockTestFunction';
import { drinksDetail } from 'src/mocks/drinksDetail';
import { drinksReviews } from 'src/mocks/drinksReviews';
import API from 'src/apis/requests';

import { MESSAGE, PATH } from 'src/constants';

import DrinksDetailPage from '.';

describe('로그인 되지 않은 사용자가 상세페이지를 이용한다.', () => {
  beforeEach(async () => {
    window.HTMLElement.prototype.scrollIntoView = jest.fn();
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });

    API.getUserInfo = jest.fn().mockImplementation(() => {
      throw new Error();
    });
    API.getDrink = jest.fn().mockReturnValue(drinksDetail);
    API.getReview = jest.fn().mockReturnValue(drinksReviews);

    customRender({ initialEntries: [`${PATH.DRINKS}/0`], children: <DrinksDetailPage /> });

    await waitFor(() => expect(API.getUserInfo).toBeCalled());
    await waitFor(() => expect(API.getDrink).toBeCalled());
    await waitFor(() => expect(API.getReview).toBeCalled());
  });

  it('로그인 되지 않은 사용자가 상세페이지에서 선호도를 남기려고 할 때 로그인하라는 창이 뜬다.', async () => {
    const preferenceInput = await screen.findByRole('slider');

    fireEvent.click(preferenceInput);

    const confirmText = await screen.getByText(MESSAGE.LOGIN_REQUIRED_TO_UPDATE_PREFERENCE);
    const confirmSubText = await screen.getByText(
      MESSAGE.LOGIN_REQUIRED_TO_UPDATE_PREFERENCE_SUB_MESSAGE
    );
    expect(confirmText).toBeVisible();
    expect(confirmSubText).toBeVisible();
  });
});
