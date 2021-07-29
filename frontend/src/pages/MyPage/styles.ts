import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Header = styled.header`
  ${Flex({ alignItems: 'center' })};

  position: relative;

  width: 100%;
  height: 3.5em;

  h2 {
    font-size: 1.3rem;
    font-weight: 700;
  }
`;

export { Header };
