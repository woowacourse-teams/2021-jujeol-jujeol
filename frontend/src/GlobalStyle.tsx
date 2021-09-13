import { Global, css } from '@emotion/react';
import { COLOR, Z_INDEX } from './constants';

const GlobalStyle = () => (
  <Global
    styles={css`
      * {
        font-family: 'Nanum Gothic', sans-serif;
        box-sizing: border-box;
        scroll-behavior: smooth;
      }

      html,
      body,
      #root {
        width: 100%;
        height: 100%;
        background-color: ${COLOR.PURPLE_900};
        font-size: 16px;

        position: fixed;
        overflow: hidden;
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
        bottom: 0;
        transform: translateX(-50%);
        visibility: hidden;
        z-index: ${Z_INDEX.MODAL};
      }

      #confirm {
        width: 100%;
        max-width: 480px;
        min-width: 280px;
        height: 100vh;
        position: fixed;
        left: 50%;
        bottom: 0;
        transform: translateX(-50%);
        visibility: hidden;
        z-index: ${Z_INDEX.CONFIRM};
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

        :disabled {
          color: ${COLOR.GRAY_400};
          filter: brightness(80%);
        }
      }

      input {
        -webkit-tap-highlight-color: transparent;

        @media screen and (max-width: 320px) {
          html,
          body,
          #root {
            font-size: 14px;
          }
        }
      }
    `}
  />
);

export default GlobalStyle;
