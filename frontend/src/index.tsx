import React from 'react';
import ReactDOM from 'react-dom';
import APIProvider from './apis/APIProvider';
import App from './App';
import GlobalStyle from './GlobalStyle';
import dotenv from 'dotenv';
import { UserProvider } from './contexts/UserContext';

import * as Sentry from '@sentry/react';
import { Integrations } from '@sentry/tracing';
import { setLogger } from 'react-query';

dotenv.config();

if (process.env.SNOWPACK_PUBLIC_ENV === 'PROD') {
  Sentry.init({
    dsn: process.env.SNOWPACK_PUBLIC_SENTRY_DSN,
    integrations: [new Integrations.BrowserTracing()],
    tracesSampleRate: 1.0,
  });

  setLogger({
    log: (message) => {
      Sentry.captureMessage(message);
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
