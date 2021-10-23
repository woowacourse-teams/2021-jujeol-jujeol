import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

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

const fadeOut = keyframes`
  50% {
      transform: translateY(60%);
      opacity: 0;
    }
  
  100%  {
      transform: translateY(100%);
      opacity: 0;
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

const Content = styled.div<{ isOpened: boolean; isTouchDown: boolean }>`
  max-width: 100%;
  min-width: 100%;

  height: 80vh;
  border-top-right-radius: 1.5rem;
  border-top-left-radius: 1.5rem;
  padding: 2.5rem 2rem 2rem;
  position: absolute;
  bottom: 0;
  left: 0;
  background-color: ${COLOR.WHITE};
  ${({ isOpened, isTouchDown }) =>
    isOpened
      ? css`
          animation: ${popUp} 0.6s ease-out;
        `
      : !isTouchDown &&
        css`
          animation: ${fadeOut} 1s ease-out;
          transition: visibility 1s;
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

const CloseButton = styled.button`
  opacity: 0;
  position: absolute;
  top: -1rem;
  left: 1rem;

  :focus {
    opacity: 0.5;
  }
`;

export { CloseButton, Container, Content };
