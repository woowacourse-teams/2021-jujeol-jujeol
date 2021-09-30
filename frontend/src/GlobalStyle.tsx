import { Global, css } from '@emotion/react';
import { COLOR, Z_INDEX } from './constants';
import Reset from './styles/Reset';

const MOBILE_X_SMALL = '280px';
const MOBILE_SMALL = '320px';
const MOBILE_MEDIUM = '375px';
const MOBILE_LARGE = '425px';

const GlobalStyle = () => (
  <Global
    styles={css`
      ${Reset()}

      * {
        font-family: 'Nanum Gothic', sans-serif;

        box-sizing: border-box;
        scroll-behavior: smooth;
      }

      html,
      body {
        width: 100%;
        height: 100%;
        overflow: hidden;

        background-color: ${COLOR.GRAY_100};

        font-size: 16px;
        line-height: 1.25;
      }

      #root {
        min-width: ${MOBILE_X_SMALL};
        max-width: ${MOBILE_LARGE};
        height: 100%;
        margin: 0 auto;

        position: relative;

        background-color: ${COLOR.PURPLE_900};
      }

      @media screen and (max-width: ${MOBILE_SMALL}) {
        html,
        body {
          font-size: 14px;
        }
      }

      #modal {
        width: 100%;
        min-width: ${MOBILE_X_SMALL};
        max-width: ${MOBILE_LARGE};

        visibility: hidden;
        position: fixed;
        top: 0;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        z-index: ${Z_INDEX.MODAL};
      }

      #snackbar {
        width: 100%;
        min-width: ${MOBILE_X_SMALL};
        max-width: ${MOBILE_LARGE};

        visibility: hidden;
        position: fixed;
        bottom: 10%;
        left: 50%;
        transform: translateX(-50%);
        z-index: ${Z_INDEX.SNACKBAR};
      }

      a,
      button {
        color: ${COLOR.BLACK};
        font-size: 16px;

        -webkit-tap-highlight-color: transparent;

        :disabled {
          color: ${COLOR.GRAY_400};

          filter: brightness(80%);
        }
      }
    `}
  />
);

export default GlobalStyle;
