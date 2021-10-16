import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Wrapper = styled.section`
  color: ${COLOR.BLACK};
  text-align: left;
`;

const ReviewList = styled.ul`
  display: grid;
  grid-row-gap: 1rem;
  font-size: 0.875rem;
  margin-top: 1rem;
  line-break: anywhere;

  li {
    position: relative;
  }
`;

export { Wrapper, ReviewList };
