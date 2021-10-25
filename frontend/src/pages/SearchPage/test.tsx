import { Redirect, Route, Switch } from 'react-router-dom';
import { fireEvent, screen } from '@testing-library/dom';
import { Location } from 'history';

import { PATH } from 'src/constants';
import { categories } from 'src/mocks/category';
import { customRender } from 'src/tests/customRenderer';
import { MockIntersectionObserver, mockScrollTo } from 'src/tests/mockTestFunction';
import SearchPage from '.';

import '@testing-library/jest-dom';

let testLocation: Location<unknown>;

const renderSearchPage = async () => {
  await customRender({
    initialEntries: [PATH.SEARCH],
    children: (
      <>
        <Switch>
          <Route exact path={PATH.SEARCH}>
            <SearchPage />
          </Route>
          <Route exact path={PATH.SEARCH_RESULT}>
            검색결과
          </Route>
          <Redirect to={PATH.SEARCH} />
        </Switch>

        <Route
          path="*"
          render={({ location }) => {
            testLocation = location;
            return null;
          }}
        />
      </>
    ),
  });
};

describe('사용자가 주류 검색 및 카테고리 검색을 위해 검색 페이지를 이용한다.', () => {
  beforeEach(async () => {
    await renderSearchPage();

    Object.defineProperty(global.window, 'scrollTo', { value: mockScrollTo });
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });
  });

  it('사용자는 검색 페이지에서 검색창을 확인할 수 있다.', () => {
    screen.getByRole('input');
    screen.getByPlaceholderText('검색어를 입력해주세요');
    screen.getByRole('button', { name: '검색' });
  });

  it('사용자는 검색 페이지에서 주류 카테고리 목록을 확인할 수 있다.', () => {
    screen.getByRole('list', { name: '주류 카테고리 목록' });

    categories.forEach((item) => {
      screen.getByRole('listitem', { name: `${item.name}` });
    });
  });

  it('사용자는 검색어를 입력하지 않으면 검색을 할 수 없다.', () => {
    const searchInput = screen.getByRole('input');
    const searchButton = screen.getByRole('button', { name: '검색' });

    fireEvent.change(searchInput, { target: { value: '' } });
    fireEvent.click(searchButton);

    expect(searchInput).toBeRequired();
    expect(testLocation.pathname).toBe(`${PATH.SEARCH}`);
  });

  it('사용자가 검색창에 "맥주"라고 입력하면 입력을 하면, "맥주" 단어를 검색한다.', () => {
    const searchInput = screen.getByRole('input') as HTMLInputElement;
    const searchButton = screen.getByRole('button', { name: '검색' });

    fireEvent.change(searchInput, { target: { value: '맥주' } });
    fireEvent.click(searchButton);

    expect(searchInput.value).toBe('맥주');
    expect(testLocation.pathname).not.toBe(`${PATH.SEARCH}`);
  });

  it('사용자는 맥주 카테고리를 선택하여 검색할 수 있다.', () => {
    const beerCategory = screen.getByRole('link', { name: '맥주' });
    fireEvent.click(beerCategory);

    expect(testLocation.pathname).not.toBe(`${PATH.SEARCH}`);
  });
});
