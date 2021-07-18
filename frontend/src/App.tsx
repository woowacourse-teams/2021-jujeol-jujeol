import { BrowserRouter as Router, Route, Redirect, Switch } from 'react-router-dom';
import ModalProvider from './components/Modal/ModalProvider';
import Tab from './components/Tab/Tab';
import PATH from './constants/path';
import DrinksDetailPage from './pages/DrinksDetailPage';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import OauthPage from './pages/OauthPage';
import { MainContainer } from './styles';

const App = () => {
  return (
    <>
      <Router>
        <ModalProvider>
          <MainContainer>
            <Switch>
              <Route exact path={[PATH.HOME, PATH.ROOT]} component={HomePage} />
              <Route exact path={[PATH.LOGIN]} component={LoginPage} />
              <Route exact path={[PATH.OAUTH]} component={OauthPage} />
              <Route exact path={`${PATH.DRINKS}/:id`} component={DrinksDetailPage} />
              <Route exact path={[PATH.MYPAGE]}>
                mypage
              </Route>
              <Redirect to={PATH.ROOT} />
            </Switch>
          </MainContainer>
        </ModalProvider>

        <Tab />
      </Router>
    </>
  );
};

export default App;
