import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Container = styled.section`
  width: 100%;
  height: fit-content;
  min-height: 196px;
  position: relative;

  h2 {
    opacity: 0;
    position: absolute;
    top: 0;
    left: 0;
  }

  img {
    width: 100%;
    height: 100%;
    background-color: ${COLOR.WHITE_200};
    object-fit: cover;
  }
`;

export { Container };
