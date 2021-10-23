import { screen, waitFor } from '@testing-library/react';

import API from 'src/apis/requests';
import { PATH } from 'src/constants';
import { validateMember } from 'src/mocks/member';
import { personalDrinks } from 'src/mocks/personalDrinks';
import { customRender } from 'src/tests/customRenderer';
import { MockIntersectionObserver, mockMatchMedia, mockScrollTo } from 'src/tests/mockTestFunction';
import MyDrinksPage from '.';

import '@testing-library/jest-dom';

describe('로그인 된 사용자가 선호도를 남긴 술 페이지를 이용한다.', () => {
  beforeAll(async () => {
    Object.defineProperty(global.window, 'scrollTo', { value: mockScrollTo });
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });
    Object.defineProperty(window, 'matchMedia', {
      writable: true,
      value: mockMatchMedia,
    });

    jest.spyOn(window, 'alert').mockImplementation(() => {
      return;
    });
    jest.spyOn(window, 'confirm').mockImplementation(() => {
      return true;
    });

    API.getUserInfo = jest.fn().mockReturnValue(validateMember);
    API.getPersonalDrinks = jest.fn().mockReturnValue(personalDrinks);

    await customRender({
      initialEntries: [PATH.MY_DRINKS],
      children: <MyDrinksPage />,
    });

    await waitFor(() => expect(API.getUserInfo).toBeCalled());
    await waitFor(() => expect(API.getPersonalDrinks).toBeCalled());
  });

  it('사용자는 선호도를 남긴 술 목록을 확인할 수 있다.', async () => {
    expect(screen.getByRole('banner')).toHaveTextContent('선호도를 남긴 술');

    const drinkList = screen.getByRole('list', { name: '선호도를 남긴 술' }).querySelectorAll('li');

    personalDrinks.data.every((drink, index) => {
      expect(drinkList[index]).toHaveTextContent(drink.name);
      expect(drinkList[index]).toHaveTextContent(drink.preferenceRate.toString());
      expect(drinkList[index].querySelector('img')).toHaveAttribute('src', drink.imageUrl);
      expect(drinkList[index].querySelector('img')).toHaveAccessibleName(drink.name);
    });
  });
});
