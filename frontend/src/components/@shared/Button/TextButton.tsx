import styled from '@emotion/styled';

import { COLOR } from 'src/constants';

const TextButton = styled.button<React.CSSProperties>`
  margin: ${({ margin }) => margin || '0'};
  padding: 1rem 2rem;

  display: block;

  font-size: ${({ fontSize }) => fontSize || '1rem'};
  text-align: center;
  color: ${({ color }) => color || COLOR.BLACK};
  background-color: transparent;
  border: none;
`;

export default TextButton;
