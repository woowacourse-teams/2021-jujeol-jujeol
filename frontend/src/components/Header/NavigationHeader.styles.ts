import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';
import Header from '../@shared/Header/Header';

const NavHeader = styled(Header)`
  ${Flex({ alignItems: 'center' })};

  width: 100%;
  position: relative;

  h2 {
    font-size: 1.3rem;
    font-weight: 700;
  }

  button {
    background-color: transparent;
    border: 0;
    margin-left: 0.5rem;
  }
`;

export { NavHeader };
