import styled from '@emotion/styled';

import { COLOR } from 'src/constants';

const Container = styled.section`
  width: 100%;
  height: fit-content;
  min-height: 196px;
  position: relative;

  img {
    width: 100%;
    height: 100%;
    background-color: ${COLOR.GRAY_100};
    object-fit: cover;
  }
`;

export { Container };
