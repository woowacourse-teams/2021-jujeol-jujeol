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
  size: string;
  borderWidth: string;
  dir: keyof typeof DIRECTION;
}

const Arrow = styled.div<Omit<ArrowStyle, 'translate'>>`
  line-height: 1.5;

  display: inline-block;
  width: ${({ size }) => size ?? '0.7rem'};
  height: ${({ size }) => size ?? '0.7rem'};

  border-top: ${({ borderWidth }) => borderWidth ?? '0.125rem'};
  border-right: ${({ borderWidth }) => borderWidth ?? '0.125rem'};
  border-top-color: ${({ color }) => color ?? `${COLOR.WHITE_200}`};
  border-right-color: ${({ color }) => color ?? `${COLOR.WHITE_200}`};
  border-top-style: solid;
  border-right-style: solid;
  transform: ${({ dir }) => `rotate(${DIRECTION[dir]})`};
`;

export default Arrow;
