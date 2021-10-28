import styled from '@emotion/styled';

import COLOR from './constants/color';

const MainContainer = styled.main`
  padding-bottom: 5rem;
  box-sizing: border-box;
  color: ${COLOR.WHITE};
  width: 100%;
  height: 100%;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
`;

export { MainContainer };
