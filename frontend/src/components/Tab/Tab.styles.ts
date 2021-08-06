import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { COLOR, Z_INDEX } from 'src/constants';

const Nav = styled.nav<{ keyboardUp: boolean }>`
  max-width: 100%;
  min-width: 100%;
  height: 3rem;
  padding: 0.3rem;
  background-color: ${COLOR.WHITE_100};
  position: sticky;
  bottom: 0;
  left: 0;
  z-index: ${Z_INDEX.TAB_BAR};
  ${({ keyboardUp }) =>
    keyboardUp &&
    css`
      display: none;
    `}
  ul {
    display: flex;
    justify-content: space-between;
    width: 100%;

    li {
      width: 100%;

      a {
        display: block;
        width: 100%;
        height: 100%;
        font-size: 0.8rem;
        text-align: center;

        svg {
          width: 1.3rem;
          height: 1.3rem;
          transition: transform 0.25s ease;
        }
      }

      a.active {
        color: ${COLOR.PURPLE_200};
        font-weight: 700;

        svg {
          transform: scale(1.4) translateY(-20%);
          fill: ${COLOR.PURPLE_200};
          stroke-width: 0.1rem;
          stroke: ${COLOR.WHITE_100};
        }
      }
    }
  }
`;

export { Nav };
