import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const DIRECTION = {
  UP: '315deg',
  DOWN: '135deg',
  LEFT: '225deg',
  RIGHT: '45deg',
};

interface ArrowStyle {
  fontSize?: string;
  size?: string;
  borderWidth?: string;
  dir: keyof typeof DIRECTION;
}

const Arrow = styled.div<Omit<ArrowStyle, 'translate'>>`
  line-height: 1.5;

  display: inline-block;
  width: ${({ size }) => size ?? '0.7rem'};
  height: ${({ size }) => size ?? '0.7rem'};

  border-top: ${({ borderWidth = '0.125rem', color = COLOR.GRAY_100 }) =>
    `${borderWidth} solid ${color}`};
  border-right: ${({ borderWidth = '0.125rem', color = COLOR.GRAY_100 }) =>
    `${borderWidth} solid ${color}`};
  transform: ${({ dir }) => `rotate(${DIRECTION[dir]})`};
`;

export default Arrow;
