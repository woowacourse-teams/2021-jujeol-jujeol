import { FormEvent } from 'react';
import { BrowserRouter as Router, Route, Redirect } from 'react-router-dom';
import SearchBar from './components/@shared/SearchBar/SearchBar';
import Header from './components/Header/Header';
import Tab from './components/Tab/Tab';
import PATH from './constants/path';
import HomePage from './pages/HomePage';
import { MainContainer, Logo } from './styles';

const App = () => {
  const onSearch = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
  };

  return (
    <>
      <Router>
        <Header>
          <Logo>주절주절</Logo>
          <SearchBar placeholder="검색어를 입력해주세요" onSubmit={onSearch} />
        </Header>

        <MainContainer>
          <Route exact path={[PATH.HOME, PATH.ROOT]} component={HomePage} />
          <Route exact path={[PATH.LOGIN]}>
            login
          </Route>
          <Redirect to={PATH.ROOT} />
        </MainContainer>

        <Tab></Tab>
      </Router>
    </>
  );
};

export default App;
