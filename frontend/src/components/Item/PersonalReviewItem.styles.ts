import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Item = styled.li`
  margin-bottom: 1rem;
  height: 100%;
  display: flex;
  align-items: center;

  > div {
    ${Flex({ alignItems: 'start' })}
  }
`;

export { Item };
