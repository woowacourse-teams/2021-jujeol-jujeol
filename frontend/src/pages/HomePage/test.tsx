import '@testing-library/jest-dom';
import { screen, render, waitFor } from '@testing-library/react';
import APIProvider from 'src/apis/APIProvider';
import HomePage from '.';
import API from 'src/apis/requests';
import drinks from 'src/mock/drinks';

describe('사용자는 홈 화면에서 주류 목록을 조회할 수 있다.', () => {
  beforeAll(async () => {
    API.getDrinks = jest.fn().mockReturnValue({ data: drinks, count: drinks.length });

    render(
      <APIProvider>
        <HomePage />
      </APIProvider>
    );
    await waitFor(() => expect(API.getDrinks).toBeCalledTimes(1));
  });

  it('사용자는 홈 화면에서 주류 목록을 조회할 수 있다.', async () => {
    expect(screen.getByText('오늘 이런 술 어때요?')).toBeVisible();
    expect(screen.getByText('회원님을 위해 준비했어요')).toBeVisible();

    drinks.every((drink) => expect(screen.getByText(drink.name)).toBeVisible());
  });
});
