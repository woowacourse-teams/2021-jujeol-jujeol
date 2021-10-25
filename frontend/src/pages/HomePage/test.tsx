import { screen, waitFor } from '@testing-library/react';

import API from 'src/apis/requests';
import { PATH } from 'src/constants';
import { drinks } from 'src/mocks/drinks';
import { customRender } from 'src/tests/customRenderer';
import HomePage from '.';

import '@testing-library/jest-dom';

describe('사용자는 홈 화면에서 주류 목록을 조회할 수 있다.', () => {
  beforeAll(async () => {
    API.getDrinks = jest.fn().mockReturnValue(drinks);

    customRender({ initialEntries: [PATH.HOME], children: <HomePage /> });

    await waitFor(() => expect(API.getDrinks).toBeCalled());
  });

  it('사용자는 홈 화면에서 주류 목록을 조회할 수 있다.', async () => {
    const recommendationSectionHeader = screen.getByRole('heading', {
      name: /오늘 이런 술 어때요\?/i,
    });
    const recommendationSection = recommendationSectionHeader.closest('section');

    drinks.data.every((drink) => expect(recommendationSection).toHaveTextContent(drink.name));
  });
});
