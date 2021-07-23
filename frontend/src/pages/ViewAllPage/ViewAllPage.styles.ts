import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Title = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  padding: 0 0.5rem;

  h1 {
    font-size: 1.25rem;
    font-weight: 700;
  }

  button {
    width: 2rem;
    height: 2rem;
    background-color: transparent;
    color: ${COLOR.WHITE_100};
    border: 0;
    margin: 0;
  }
`;

export { Title };
