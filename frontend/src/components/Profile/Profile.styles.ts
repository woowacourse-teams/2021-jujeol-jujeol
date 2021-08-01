import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Container = styled.section`
  display: flex;
  align-items: center;

  width: 100%;
  height: 7rem;
  padding: 2rem;

  background-color: ${COLOR.PURPLE_500};

  div {
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    height: 2.5rem;
    margin-left: 1rem;

    h3 {
      font-size: 1.1rem;
      font-weight: 700;
    }

    p {
      font-size: 0.8rem;
      font-weight: 400;
    }
  }

  svg {
    width: 72px;
    height: 72px;
  }
`;

export { Container };
