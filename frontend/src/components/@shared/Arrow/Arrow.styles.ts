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

const ArrowShape = styled.div<Omit<ArrowStyle, 'translate'>>`
  line-height: 1.5;

  display: inline-block;

  width: ${({ size }) => size ?? '0.7rem'};
  height: ${({ size }) => size ?? '0.7rem'};

  border-top: ${({ borderWidth }) => borderWidth ?? '0.125rem'} solid ${COLOR.WHITE_200};
  border-right: ${({ borderWidth }) => borderWidth ?? '0.125rem'} solid ${COLOR.WHITE_200};
  transform: ${({ dir }) => `rotate(${DIRECTION[dir]})`};
`;

export { ArrowShape };
export type { ArrowStyle };
