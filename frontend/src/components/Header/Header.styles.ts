import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Container = styled.header`
  width: 100%;
  height: 3rem;
  padding: 1rem 0;
  margin-bottom: 1rem;
  background: linear-gradient(${COLOR.PURPLE_900} 0%, ${COLOR.PURPLE_700} 100%);
  display: flex;
  justify-content: center;
  align-items: center;
`;

export { Container };
