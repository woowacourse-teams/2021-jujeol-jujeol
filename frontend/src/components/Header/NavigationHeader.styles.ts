import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';
import Header from '../@shared/Header/Header';

const NavHeader = styled(Header)`
  ${Flex({ alignItems: 'center' })};

  width: 100%;
  position: relative;

  button {
    background-color: transparent;
    border: 0;
    margin-left: 0.5rem;
  }
`;

export { NavHeader };
