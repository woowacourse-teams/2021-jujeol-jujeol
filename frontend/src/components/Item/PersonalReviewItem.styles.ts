import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Item = styled.li`
  margin-bottom: 1rem;
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;

  > a {
    width: 100%;

    > div {
      ${Flex({ alignItems: 'start' })}
    }
  }
`;

export { Item };
