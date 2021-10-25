import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';
import { hiddenStyle } from 'src/styles/hidden';

const SkipNav = styled.nav`
  ${hiddenStyle};

  :focus-within {
    width: 100%;
    height: 3rem;
    background: black;
    opacity: 1;
    padding: 0 1rem;

    ${Flex({ justifyContent: 'space-between' })}
  }

  a {
    ${hiddenStyle};

    color: white;
    text-align: center;
  }

  a:focus {
    width: 100%;
    height: 100%;
    padding: 1rem 0;
    opacity: 1;
  }
`;

export default SkipNav;
