import React from 'react';
import { BrowserRouter as Router, Route, Redirect } from 'react-router-dom';
import Tab from './components/Tab/Tab';
import PATH from './constants/path';
import HomePage from './pages/HomePage';
import { MainContainer } from './styles';

const App = () => {
  return (
    <>
      <Router>
        <header>
          <h1>주절주절</h1>
          <input placeholder="search" />
          <button type="button">search</button>
        </header>

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
