import styled from '@emotion/styled';

import { COLOR, Z_INDEX } from 'src/constants';
import Flex from 'src/styles/Flex';

export const Container = styled.div`
  padding: 0 1rem;

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  background-color: ${COLOR.PURPLE_900};

  z-index: ${Z_INDEX.ERROR_PAGE};

  h3 {
    margin: 1.4rem 0;

    text-align: center;
  }

  a {
    padding: 1rem 3rem;
    margin-top: 1rem;

    background-color: ${COLOR.YELLOW_300};
    border-radius: 1rem;
    color: ${COLOR.BLACK};

    text-align: center;
  }
`;
