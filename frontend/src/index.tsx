import React from 'react';
import ReactDOM from 'react-dom';
import APIProvider from './apis/APIProvider';
import App from './App';
import GlobalStyle from './GlobalStyle';

ReactDOM.render(
  <React.StrictMode>
    <APIProvider>
      <GlobalStyle />
      <App />
    </APIProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
