import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const DIRECTION = {
  UP: '315deg',
  DOWN: '135deg',
  LEFT: '225deg',
  RIGHT: '45deg',
};

interface ArrowButtonStyle {
  fontSize?: string;
  size: string;
  borderSize: string;
  dir: keyof typeof DIRECTION;
}

const Button = styled.button<Omit<ArrowButtonStyle, 'translate'>>`
  background: transparent;
  border: none;
  ${({ fontSize }) => fontSize && `font-size:  ${fontSize}`};
  padding: 0 0.6rem;
  color: inherit;

  &:after {
    content: '';
    display: inline-block;

    width: ${({ size }) => size ?? '0.7rem'};
    height: ${({ size }) => size ?? '0.7rem'};

    border-top: ${({ borderSize }) => borderSize ?? '0.125rem'} solid ${COLOR.WHITE_200};
    border-right: ${({ borderSize }) => borderSize ?? '0.125rem'} solid ${COLOR.WHITE_200};
    transform: ${({ dir }) => `rotate(${DIRECTION[dir]})`};
  }
`;

export { Button };
export type { ArrowButtonStyle };
