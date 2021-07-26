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
    margin-bottom: 0.2rem;
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

const InfinityScrollPoll = styled.div`
  width: 100%;
  height: 13rem;
  position: absolute;
  bottom: 0;
  left: 0;
  background-color: transparent;
`;

export { Wrapper, ReviewList, InfinityScrollPoll };
