import styled from '@emotion/styled';
import { css, keyframes } from '@emotion/react';
import { COLOR, Z_INDEX } from 'src/constants';

const popUp = keyframes`
  0% {
    transform: translateY(100%);
    opacity: 0;
  }

  80% {
    transform: translateY(0%);
    opacity: 1;
  }

  100% {
    transform: translateY(1%);
    opacity: 1;
  }
`;

const Container = styled.div<{ isOpened: boolean }>`
  max-width: 100%;
  min-width: 100%;

  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  position: relative;
  visibility: ${({ isOpened }) => (isOpened ? 'visible' : 'hidden')};
  z-index: ${Z_INDEX.MODAL};
`;

const Content = styled.div<{ isOpened: boolean }>`
  max-width: 100%;
  min-width: 100%;

  height: 80vh;
  border-top-right-radius: 1.5rem;
  border-top-left-radius: 1.5rem;
  padding: 2.5rem 2rem 2rem;
  position: absolute;
  bottom: 0;
  left: 0;
  background-color: ${COLOR.WHITE_100};
  animation: ${({ isOpened }) =>
    isOpened &&
    css`
      ${popUp} 0.6s ease-out
    `};
  transform: translateY(1%);

  ::before {
    content: '';
    position: absolute;
    top: 0.5rem;
    left: 50%;
    height: 0.3rem;
    width: 15%;
    background-color: ${COLOR.GRAY_200};
    border: none;
    border-radius: 24px;
    transform: translateX(-50%);
  }
`;

export { Container, Content };
