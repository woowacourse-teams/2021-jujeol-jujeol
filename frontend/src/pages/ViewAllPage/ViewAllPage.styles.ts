import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Title = styled.header`
  width: 100%;
  height: 3.5rem;
  display: flex;
  align-items: center;
  background: linear-gradient(${COLOR.PURPLE_900} 0%, ${COLOR.PURPLE_700} 100%);
  margin-bottom: 1rem;
  padding: 0 0.5rem;

  h1 {
    font-size: 1.5rem;
    font-weight: 700;
  }

  button {
    width: 2rem;
    height: 2rem;
    background-color: transparent;
    color: ${COLOR.WHITE_100};
    border: 0;
  }
`;

export { Title };
