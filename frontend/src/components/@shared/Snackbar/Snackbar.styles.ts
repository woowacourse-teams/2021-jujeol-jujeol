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
  ${Flex({
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  })}
  width: 90%;
  padding: 1rem;
  margin: 0 auto;

  background-color: ${COLOR.PURPLE_500}CC;
  color: ${COLOR.GRAY_100};
  font-size: 0.9rem;
  text-align: center;
  line-height: 1.5;

  border-radius: 0.5rem;
  /* visibility: hidden; */

  z-index: ${Z_INDEX.SNACKBAR};

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
