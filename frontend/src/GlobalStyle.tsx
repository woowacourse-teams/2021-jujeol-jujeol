import { Global, css } from '@emotion/react';
import { COLOR } from './constants';

const GlobalStyle = () => (
  <Global
    styles={css`
      @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap');

      * {
        font-family: 'Nanum Gothic', sans-serif;
        box-sizing: border-box;
      }

      html,
      body,
      #root {
        width: 100%;
        height: 100%;
        background-color: ${COLOR.PURPLE_900};
        font-size: 16px;
      }

      #root {
        position: relative;
        max-width: 480px;
        min-width: 280px;
        margin: 0 auto;
      }

      #modal {
        width: 100%;
        max-width: 480px;
        min-width: 280px;
        height: 100vh;
        position: fixed;
        left: 50%;
        top: 0;
        transform: translateX(-50%);
        visibility: hidden;
        z-index: 10;
      }

      html,
      body,
      h1,
      h2,
      h3,
      h4,
      h5,
      h6,
      p,
      a,
      img,
      ol,
      ul,
      li,
      fieldset,
      form,
      label,
      legend,
      article,
      footer,
      header,
      nav,
      section {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline;
      }

      /* HTML5 display-role reset for older browsers */
      article,
      footer,
      header,
      nav,
      section {
        display: block;
      }
      body {
        line-height: 1;
      }
      ol,
      ul {
        list-style: none;
      }
      a,
      a:link,
      a:visited,
      a:hover,
      a:active {
        color: inherit;
        text-decoration: none;
      }

      a,
      button {
        color: ${COLOR.BLACK_900};
        font-size: 16px;
        -webkit-tap-highlight-color: transparent;
      }

      input {
        -webkit-tap-highlight-color: transparent;
      }
    `}
  />
);

export default GlobalStyle;
