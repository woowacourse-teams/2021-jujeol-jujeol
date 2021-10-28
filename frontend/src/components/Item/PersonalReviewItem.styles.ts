import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';

const Item = styled.li`
  width: 100%;

  > a {
    width: 100%;

    > div {
      ${Flex({ alignItems: 'start' })}
    }
  }
`;

export { Item };
