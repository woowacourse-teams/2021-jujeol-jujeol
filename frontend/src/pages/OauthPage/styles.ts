import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const wave = keyframes`
  50% {
    transform: translateY(-20%)
  }
`;

const Container = styled.div`
  width: 100%;
  height: 100%;

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  svg {
    width: 5rem;
    height: 5rem;
    margin-bottom: 2rem;
  }

  p {
    font-size: 1.5rem;
    animation: ${wave} 1.5s ease-in-out infinite;
  }
`;

export { Container };
