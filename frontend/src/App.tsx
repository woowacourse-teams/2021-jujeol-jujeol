import { lazy, Suspense } from 'react';
import { BrowserRouter as Router, Route, Redirect, Switch } from 'react-router-dom';

import ModalProvider from './components/Modal/ModalProvider';
import SnackbarProvider from './components/@shared/Snackbar/SnackbarProvider';
import Tab from './components/Tab/Tab';
import PATH from './constants/path';
import { MainContainer } from './styles';

const HomePage = lazy(() => import('./pages/HomePage'));
const LoginPage = lazy(() => import('./pages/LoginPage'));
const OauthPage = lazy(() => import('./pages/OauthPage'));
const DrinksDetailPage = lazy(() => import('./pages/DrinksDetailPage'));
const MyPage = lazy(() => import('./pages/MyPage'));
const ViewAllPage = lazy(() => import('./pages/ViewAllPage'));
const MyDrinksPage = lazy(() => import('./pages/MyDrinksPage'));
const MyReviewsPage = lazy(() => import('./pages/MyReviewsPage'));
const SearchPage = lazy(() => import('./pages/SearchPage'));
const SearchResultPage = lazy(() => import('./pages/SearchResultPage'));
const PreferencePage = lazy(() => import('./pages/PreferencePage'));

const App = () => {
  return (
      <>
        <Router>
          <SnackbarProvider>
            <ModalProvider>
              <MainContainer>
                <Switch>
                  <Suspense fallback>
                    <Route exact path={[PATH.HOME, PATH.ROOT]} component={HomePage} />
                    <Route exact path={[PATH.LOGIN]} component={LoginPage} />
                    <Route exact path={[PATH.OAUTH]} component={OauthPage} />
                    <Route exact path={[PATH.VIEW_ALL]} component={ViewAllPage} />
                    <Route exact path={`${PATH.DRINKS}/:id`} component={DrinksDetailPage} />
                    <Route exact path={[PATH.MYPAGE]} component={MyPage} />
                    <Route exact path={[PATH.MY_DRINKS]} component={MyDrinksPage} />
                    <Route exact path={[PATH.MY_REVIEWS]} component={MyReviewsPage} />
                    <Route exact path={[PATH.SEARCH]} component={SearchPage} />
                    <Route exact path={[PATH.SEARCH_RESULT]} component={SearchResultPage} />
                    <Route exact path={[PATH.PREFERENCE]} component={PreferencePage} />
                    <Redirect to={PATH.ROOT} />
                  </Suspense>
                </Switch>
              </MainContainer>
            </ModalProvider>
          </SnackbarProvider>

          <Tab />
        </Router>
      </>
  );
};

export default App;
