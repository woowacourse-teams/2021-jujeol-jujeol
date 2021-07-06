import React from 'react';
import { BrowserRouter as Router, Route, Redirect } from 'react-router-dom';
import PATH from './constants/path';
import HomePage from './pages/HomePage';

const App = () => {
  return (
    <>
      <Router>
        <Route exact path={[PATH.HOME, PATH.ROOT]} component={HomePage} />
        <Redirect to={PATH.ROOT} />
      </Router>
    </>
  );
};

export default App;
