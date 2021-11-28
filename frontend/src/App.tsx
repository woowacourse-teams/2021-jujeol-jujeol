import { lazy, Suspense, useEffect } from 'react';
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';

import SnackbarProvider from './components/@shared/Snackbar/SnackbarProvider';
import ConfirmProvider from './components/Confirm/ConfirmProvider';
import ModalProvider from './components/Modal/ModalProvider';
import Tab from './components/Tab/Tab';
import PATH from './constants/path';
import { MainContainer } from './styles';

const HomePage = lazy(() => import('./pages/HomePage'));
const LoginPage = lazy(() => import('./pages/LoginPage'));
const OauthPage = lazy(() => import('./pages/OauthPage'));
const DrinksDetailPage = lazy(() => import('./pages/DrinksDetailPage'));
const MyPage = lazy(() => import('./pages/MyPage'));
const DrinksListPage = lazy(() => import('./pages/DrinksListPage'));
const MyDrinksPage = lazy(() => import('./pages/MyDrinksPage'));
const MyReviewsPage = lazy(() => import('./pages/MyReviewsPage'));
const SearchPage = lazy(() => import('./pages/SearchPage'));
const SearchResultPage = lazy(() => import('./pages/SearchResultPage'));
const PreferencePage = lazy(() => import('./pages/PreferencePage'));
const ErrorPage = lazy(() => import('./pages/ErrorPage'));
const NonExistencePage = lazy(() => import('./pages/NonExistencePage'));

const App = () => {
  return (
    <>
      <Router>
        <ConfirmProvider>
          <SnackbarProvider>
            <ModalProvider>
              <MainContainer>
                <Suspense fallback>
                  <Switch>
                    <Route exact path={[PATH.ERROR_PAGE]} component={ErrorPage} />
                    <Route exact path={[PATH.HOME, PATH.ROOT]} component={HomePage} />
                    <Route exact path={[PATH.LOGIN]} component={LoginPage} />
                    <Route exact path={[PATH.OAUTH]} component={OauthPage} />
                    <Route exact path={[PATH.DRINKS]} component={DrinksListPage} />
                    <Route exact path={`${PATH.DRINKS}/:id`} component={DrinksDetailPage} />
                    <Route exact path={[PATH.MYPAGE]} component={MyPage} />
                    <Route exact path={[PATH.MY_DRINKS]} component={MyDrinksPage} />
                    <Route exact path={[PATH.MY_REVIEWS]} component={MyReviewsPage} />
                    <Route exact path={[PATH.SEARCH]} component={SearchPage} />
                    <Route exact path={[PATH.SEARCH_RESULT]} component={SearchResultPage} />
                    <Route exact path={[PATH.PREFERENCE]} component={PreferencePage} />
                    <Route exact path={[PATH.NON_EXISTENCE_PAGE]} component={NonExistencePage} />
                    <Redirect to={PATH.NON_EXISTENCE_PAGE} />
                  </Switch>
                </Suspense>
              </MainContainer>
            </ModalProvider>
          </SnackbarProvider>
        </ConfirmProvider>

        <Tab />
      </Router>
    </>
  );
};

export default App;
