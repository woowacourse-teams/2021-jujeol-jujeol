import { BrowserRouter as Router, Route, Redirect, Switch } from 'react-router-dom';
import Tab from './components/Tab/Tab';
import PATH from './constants/path';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import { MainContainer } from './styles';

const App = () => {
  return (
    <>
      <Router>
        <MainContainer>
          <Switch>
            <Route exact path={[PATH.HOME, PATH.ROOT]} component={HomePage} />
            <Route exact path={[PATH.LOGIN]} component={LoginPage} />
            <Redirect to={PATH.ROOT} />
          </Switch>
        </MainContainer>

        <Tab></Tab>
      </Router>
    </>
  );
};

export default App;
