import '@testing-library/jest-dom';
import { screen, waitFor } from '@testing-library/react';
import { customRender } from 'src/tests/customRenderer';
import { drinks } from 'src/mocks/drinks';
import API from 'src/apis/requests';

import { PATH } from 'src/constants';

import HomePage from '.';

describe('사용자는 홈 화면에서 주류 목록을 조회할 수 있다.', () => {
  beforeAll(async () => {
    API.getDrinks = jest.fn().mockReturnValue(drinks);
    API.getRecommendedDrinks = jest.fn().mockReturnValue(drinks);

    customRender({ initialEntries: [PATH.HOME], children: <HomePage /> });

    await waitFor(() => expect(API.getDrinks).toBeCalled());
    await waitFor(() => expect(API.getRecommendedDrinks).toBeCalled());
  });

  it('사용자는 홈 화면에서 주류 목록을 조회할 수 있다.', async () => {
    const recommendationSectionHeader = screen.getByRole('heading', {
      name: /오늘 이런 술 어때요\?/i,
    });
    const recommendationSection = recommendationSectionHeader.closest('section');
    const viewAllSectionHeader = screen.getByRole('heading', {
      name: /전체보기/i,
    });
    const viewAllSection = viewAllSectionHeader.closest('section');

    drinks.data.every((drink) => expect(recommendationSection).toHaveTextContent(drink.name));
    drinks.data.slice(0, 3).every((drink) => expect(viewAllSection).toHaveTextContent(drink.name));
  });
});
