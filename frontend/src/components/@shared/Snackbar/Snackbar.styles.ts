import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import { COLOR, Z_INDEX } from 'src/constants';
import Flex from 'src/styles/Flex';

const fadeInOut = keyframes`
  0% { opacity: 0; }
  10% { opacity: 1; }
  
  90% { opacity: 1; }
  100% { opacity: 0; }
`;

const Container = styled.section<{ message: boolean }>`
  width: 90%;

  padding: 1rem;
  margin: 0 auto;

  visibility: hidden;
  z-index: ${Z_INDEX.SNACKBAR};

  ${Flex({
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  })}

  background-color: ${COLOR.PURPLE_600}EE;
  border-radius: 0.5rem;

  font-size: 0.9rem;
  color: ${COLOR.GRAY_100};
  text-align: center;

  ${({ message }) =>
    message &&
    css`
      visibility: visible;

      animation: ${fadeInOut} 3s ease-out;
    `}

  > span {
    margin-bottom: 0.5rem;
  }
`;

export { Container };
