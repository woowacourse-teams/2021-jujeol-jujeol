import React from 'react';
import ReactDOM from 'react-dom';
import { setLogger } from 'react-query';
import * as Sentry from '@sentry/react';
import { Integrations } from '@sentry/tracing';
import dotenv from 'dotenv';

import APIProvider from './apis/APIProvider';
import App from './App';
import { UserProvider } from './contexts/UserContext';
import GlobalStyle from './GlobalStyle';

dotenv.config();

if (process.env.SNOWPACK_PUBLIC_ENV === 'PROD') {
  Sentry.init({
    dsn: process.env.SNOWPACK_PUBLIC_SENTRY_DSN,
    integrations: [new Integrations.BrowserTracing()],
    tracesSampleRate: 1.0,
  });

  setLogger({
    log: (message) => {
      console.log(message);
    },
    warn: (message) => {
      Sentry.captureMessage(message);
    },
    error: (error) => {
      Sentry.captureException(error);
    },
  });
}

ReactDOM.render(
  <React.StrictMode>
    <APIProvider>
      <UserProvider>
        <GlobalStyle />
        <App />
      </UserProvider>
    </APIProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
