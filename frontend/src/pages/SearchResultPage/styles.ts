import { COLOR } from 'src/constants';
import styled from '@emotion/styled';

const Container = styled.div`
  position: relative;
`;

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
    background-color: transparent;
    border: 0;
    margin-left: 0.5rem;
  }
`;

const ResultHeading = styled.h2`
  line-break: anywhere;
  line-height: 1.25;
  margin: 1rem 2rem;
  color: ${COLOR.WHITE_200};
`;

export { Container, Title, ResultHeading };
