import styled from '@emotion/styled';

import { COLOR } from 'src/constants';

const Container = styled.div`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;

  padding: 0 1.5rem;

  > div {
    width: 100%;
  }

  p {
    font-size: 1rem;
    color: ${COLOR.GRAY_300};
    margin-bottom: 1rem;
  }
`;

const Logo = styled.h1`
  margin-bottom: 4rem;

  img {
    width: 180px;
  }
`;

export { Container, Logo };
