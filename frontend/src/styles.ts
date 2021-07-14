import styled from '@emotion/styled';
import COLOR from './constants/color';

const MainContainer = styled.main`
  padding: 0 0 4rem;
  box-sizing: border-box;
  color: ${COLOR.WHITE_100};
  min-width: 320px;
  min-height: 100%;
  overflow-y: auto;
`;

const Logo = styled.h1`
  font-size: 1.4rem;
  font-weight: 700;
  color: ${COLOR.WHITE_100};
  margin-bottom: 1rem;
  text-align: center;
`;

export { MainContainer, Logo };
