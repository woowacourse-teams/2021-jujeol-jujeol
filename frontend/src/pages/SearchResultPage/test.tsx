import { screen, waitFor } from '@testing-library/dom';

import API from 'src/apis/requests';
import { PATH } from 'src/constants';
import { drinks } from 'src/mocks/drinks';
import { customRender } from 'src/tests/customRenderer';
import { MockIntersectionObserver, mockScrollTo } from 'src/tests/mockTestFunction';
import SearchResultPage from '.';

import '@testing-library/jest-dom';

describe('사용자는 검색 결과를 확인할 수 있다.', () => {
  beforeEach(async () => {
    Object.defineProperty(global.window, 'scrollTo', { value: mockScrollTo });
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });
    Object.defineProperty(global.window, 'location', {
      value: {},
    });

    API.getDrinks = jest.fn().mockReturnValue(drinks);
    API.getSearchResults = jest.fn().mockReturnValue({
      data: [],
      pageInfo: {
        currentPage: 1,
        lastPage: 1,
        countPerPage: 10,
        totalSize: 0,
      },
    });

    await customRender({
      initialEntries: [`${PATH.SEARCH_RESULT}/search/result?words=없는술검색`],
      children: <SearchResultPage />,
    });

    await waitFor(() => expect(API.getSearchResults).toBeCalled());
    await waitFor(() => expect(API.getDrinks).toBeCalled());
  });

  it('사용자는 검색 결과가 없음을 확인할 수 있다.', async () => {
    const noResultText = screen.getByRole('heading', { name: '검색결과 0건' });
    expect(noResultText).toBeVisible();
  });

  it('사용자는 검색 결과가 없을 때 추천 데이터를 확인할 수 있다.', async () => {
    const recommendationDrinksTitle = screen.getByRole('list', {
      name: '검색 결과가 없을 때 술 추천 리스트',
    });
    expect(recommendationDrinksTitle).toBeVisible();
  });
});

describe('사용자는 검색 결과를 확인할 수 있다.', () => {
  beforeEach(async () => {
    Object.defineProperty(global.window, 'scrollTo', { value: mockScrollTo });
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });
  });

  it('사용자는 검색 결과를 확인할 수 있다.', async () => {
    API.getSearchResults = jest.fn().mockReturnValue(drinks);
    await customRender({
      initialEntries: [`${PATH.SEARCH_RESULT}/search/result?words=타이거 라들러`],
      children: <SearchResultPage />,
    });

    await waitFor(() => expect(API.getSearchResults).toBeCalled());

    const searchResults = screen.getByRole('list');
    expect(searchResults.children.length).toBe(drinks.data.length);
  });
});
