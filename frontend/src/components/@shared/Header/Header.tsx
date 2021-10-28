import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Header = styled.header`
  width: 100%;
  height: 3rem;
  padding: 1rem 0;

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  background: linear-gradient(${COLOR.PURPLE_900} 0%, ${COLOR.PURPLE_800}CC 100%);
`;

export default Header;
