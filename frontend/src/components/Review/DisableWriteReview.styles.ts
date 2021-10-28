import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.div`
  > div {
    ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};
    margin-bottom: 1rem;

    p {
      color: ${COLOR.GRAY_100};
      word-break: keep-all;
      font-size: 0.9rem;
    }
  }
`;

export { Container };
