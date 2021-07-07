import styled from '@emotion/styled';
import COLOR from './constants/color';

const MainContainer = styled.main`
  padding: 1.5rem 0 4rem;
  box-sizing: border-box;
  background-color: ${COLOR.PURPLE_900};
  color: ${COLOR.WHITE_100};
  min-width: 320px;
  min-height: 100%;
  overflow-y: auto;
`;

export { MainContainer };
