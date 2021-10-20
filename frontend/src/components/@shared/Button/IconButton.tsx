import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { SerializedStyles } from '@emotion/serialize';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const SIZE = {
  XX_SMALL: css`
    width: 1.5rem;
    height: 1.5rem;
  `,
  X_SMALL: css`
    width: 2rem;
    height: 2rem;
  `,
  SMALL: css`
    width: 2.5rem;
    height: 2.5rem;
  `,
  MEDIUM: css`
    width: 3rem;
    height: 3rem;
  `,
};

interface IconButtonProps extends React.CSSProperties {
  css?: SerializedStyles;
  size?: keyof typeof SIZE;
  hidden?: boolean;
}

const IconButton = styled.button<IconButtonProps>`
  ${({ size }) => (size ? SIZE[size] : SIZE.MEDIUM)};
  margin: ${({ margin }) => margin || '0'};

  ${Flex({ justifyContent: 'center', alignItems: 'center' })}
  ${({ hidden }) => hidden && `visibility: hidden`};

  border: none;
  background-color: transparent;

  color: ${({ color }) => color || COLOR.WHITE};

  ${({ css }) => css};
`;

export default IconButton;
