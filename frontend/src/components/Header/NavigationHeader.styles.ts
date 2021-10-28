import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';
import Header from '../@shared/Header/Header';

const NavHeader = styled(Header)`
  ${Flex({ alignItems: 'center' })};

  width: 100%;
  position: relative;
`;

export { NavHeader };
