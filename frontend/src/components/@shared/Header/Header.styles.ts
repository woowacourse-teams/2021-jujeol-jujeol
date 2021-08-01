import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.header`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}
  width: 100%;
  height: 3rem;
  padding: 1rem 0;
  margin-bottom: 1rem;
  background: linear-gradient(${COLOR.PURPLE_900} 0%, ${COLOR.PURPLE_700} 100%);
`;

export { Container };
