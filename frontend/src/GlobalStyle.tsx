import { Global, css } from '@emotion/react';

const GlobalStyle = () => (
  <Global
    styles={css`
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
    `}
  />
);

export default GlobalStyle;
