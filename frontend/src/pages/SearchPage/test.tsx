import '@testing-library/jest-dom';
import { createMemoryHistory } from 'history';
import { fireEvent, screen } from '@testing-library/react';
import { Redirect, Route, Switch } from 'react-router-dom';
import { Location } from 'history';

import { customRender } from 'src/tests/customRenderer';
import { PATH } from 'src/constants';
import SearchPage from '.';
import { categories } from 'src/mocks/category';

let testLocation: Location<unknown>;

const renderSearchPage = async () => {
  const history = createMemoryHistory();

  await customRender({
    initialEntries: [PATH.HOME],
    children: (
      <>
        <Switch>
          <Route exact path={PATH.SEARCH}>
            <SearchPage history={history} />
          </Route>
          <Route exact path={PATH.SEARCH_RESULT}>
            검색 결과 페이지
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

describe('사용자는 검색 기능을 사용할 수 있다.', () => {
  beforeEach(async () => {
    await renderSearchPage();
  });

  it('사용자가 검색페이지에 진입하면, 검색 입력창이 포커싱 된다.', () => {
    const SearchBar = screen.getByRole('searchbox');

    expect(SearchBar).toHaveFocus();
  });

  it('사용자는 카테고리를 확인할 수 있다.', () => {
    const CategoryItems = screen.getAllByRole('listitem');

    CategoryItems.forEach((CategoryItem, index) => {
      expect(CategoryItem).toHaveAccessibleName(categories[index].name);
    });
  });

  it('검색은 한 글자 이상을 입력해야 한다.', () => {
    const SearchBar = screen.getByRole('searchbox') as HTMLInputElement;
    const SearchButton = screen.getByRole('button', { name: '검색' });

    fireEvent.click(SearchButton);
    expect(SearchBar).toBeRequired();
  });

  it('검색창에 입력한 글자를 한 번에 지울 수 있다.', async () => {
    const SearchBar = screen.getByRole('searchbox') as HTMLInputElement;
    fireEvent.change(SearchBar, { target: { value: '맥주' } });

    const SearchResetButton = screen.getByRole('button', { name: '검색 초기화 버튼' });
    fireEvent.click(SearchResetButton);
    expect(SearchBar.value).toBe('');
  });

  it('사용자가 찾고자하는 단어를 입력하고, 검색 버튼을 누르면 검색 페이지로 이동한다.', () => {
    const SearchBar = screen.getByRole('searchbox') as HTMLInputElement;
    const SearchButton = screen.getByRole('button', { name: '검색' });

    fireEvent.change(SearchBar, { target: { value: '맥주' } });
    expect(SearchBar.value).toBe('맥주');

    fireEvent.click(SearchButton);
    expect(testLocation.pathname).toBe('/search/result');
  });
});
