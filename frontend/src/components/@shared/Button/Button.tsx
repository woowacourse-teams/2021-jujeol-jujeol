import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const SIZE = {
  SMALL: css`
    --height: 2.5rem;
    height: 2.5rem;

    font-size: 0.8rem;
  `,
  MEDIUM: css`
    --height: 3rem;
    height: 3rem;

    font-size: 1rem;
  `,
};

const SHAPE = {
  RECTANGLE: css`
    border-radius: 0.75rem;
  `,
  ROUND: css`
    border-radius: calc(var(--height) * 0.5);
  `,
};

interface ButtonProps extends React.CSSProperties {
  shape?: keyof typeof SHAPE;
  size?: keyof typeof SIZE;
}

const Button = styled.button<ButtonProps>`
  width: ${({ width }) => width || '100%'};
  ${({ size }) => (size ? SIZE[size] : SIZE.MEDIUM)}
  margin: ${({ margin }) => margin || '0'};
  padding: 0 1rem;

  ${Flex({ justifyContent: 'center', alignItems: 'center' })}

  border: none;
  ${({ shape }) => (shape ? SHAPE[shape] : SHAPE.RECTANGLE)}

  color: ${({ color }) => color || COLOR.BLACK};
  background-color: ${({ backgroundColor }) => backgroundColor || COLOR.YELLOW_300};
`;

export default Button;
