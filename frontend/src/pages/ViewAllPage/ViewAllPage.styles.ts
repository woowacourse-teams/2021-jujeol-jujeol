import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';

const Container = styled.div`
  ${Flex({ flexDirection: 'column' })}
  row-gap: 1rem;
  position: relative;
`;

const InfinityScrollPoll = styled.div`
  width: 100%;
  height: 2rem;
  background-color: transparent;
`;

export { Container, InfinityScrollPoll };
