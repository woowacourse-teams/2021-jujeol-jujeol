import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Container = styled.li`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

  :last-child {
    position: relative;
    &::after {
      content: '';
      position: absolute;
      right: -1.5rem;
      width: 1.5rem;
      height: 100%;
    }
  }

  p {
    margin: 0.5rem 0 0.3rem;
    font-size: 0.95rem;
  }
`;

export { Container };
