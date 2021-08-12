import React from 'react';
import ReactDOM from 'react-dom';
import APIProvider from './apis/APIProvider';
import App from './App';
import GlobalStyle from './GlobalStyle';
import dotenv from 'dotenv';
import { UserProvider } from './contexts/UserContext';
import { setLogger } from 'react-query';

dotenv.config();

if (process.env.SNOWPACK_PUBLIC_ENV === 'PROD') {
  setLogger({
    log: () => {
      return;
    },
    warn: () => {
      return;
    },
    error: () => {
      return;
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
