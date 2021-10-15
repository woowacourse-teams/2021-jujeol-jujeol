import { COLOR } from 'src/constants';
import styled from '@emotion/styled';

const Container = styled.div`
  position: relative;
`;

const ResultHeading = styled.h2`
  margin: 1rem 2rem;

  color: ${COLOR.GRAY_100};
  line-break: anywhere;
`;

export { Container, ResultHeading };
