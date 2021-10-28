import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.div`
  ${Flex({ flexDirection: 'column', alignItems: 'center' })}
  width: 100%;
  padding: 1.5rem;
  color: ${COLOR.GRAY_300};

  svg {
    width: 5rem;
    height: 5rem;
    margin: 0.8rem 0;
  }

  p {
    font-size: 1rem;
    line-break: anywhere;
    line-height: 1.5;

    span {
      color: ${COLOR.GRAY_100};
      font-weight: 600;
    }
  }
`;

export { Container };
