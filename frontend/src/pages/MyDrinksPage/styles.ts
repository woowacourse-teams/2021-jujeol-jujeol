import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Header = styled.header`
  ${Flex({ alignItems: 'center' })};

  width: 100%;
  height: 3.5em;
  position: relative;

  h2 {
    font-size: 1.3rem;
    font-weight: 700;
  }
`;

const Container = styled.section`
  width: 100%;
  padding: 1rem;
`;

export { Header, Container };
