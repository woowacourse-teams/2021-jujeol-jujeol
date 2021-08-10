import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Wrapper = styled.section`
  line-height: 1.25;
  color: ${COLOR.BLACK_900};

  h2 {
    color: ${COLOR.WHITE_100};
    font-weight: 700;
    text-align: left;
    font-size: 1.25rem;
    margin-bottom: 0.5rem;
    padding-left: 0.75rem;
  }
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
