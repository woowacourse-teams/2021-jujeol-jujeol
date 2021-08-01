import '@testing-library/jest-dom';
import { screen, render, waitFor } from '@testing-library/react';
import { MemoryRouter as Router } from 'react-router-dom';
import APIProvider from 'src/apis/APIProvider';
import HomePage from '.';
import API from 'src/apis/requests';
import drinks from 'src/mocks/drinks';

describe('사용자는 홈 화면에서 주류 목록을 조회할 수 있다.', () => {
  beforeAll(async () => {
    API.getDrinks = jest.fn().mockReturnValue({ data: drinks });
    API.getRecommendedDrinks = jest.fn().mockReturnValue({ data: drinks });

    render(
      <APIProvider>
        <Router>
          <HomePage />
        </Router>
      </APIProvider>
    );
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

    drinks.every((drink) => expect(recommendationSection).toHaveTextContent(drink.name));
    drinks.slice(0, 3).every((drink) => expect(viewAllSection).toHaveTextContent(drink.name));
  });
});
