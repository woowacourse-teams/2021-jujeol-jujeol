import styled from '@emotion/styled';

const SIZE = {
  SMALL: '4.5rem',
  MEDIUM: '6.25rem',
  LARGE: '7.5rem',
};

const SHAPE = {
  CIRCLE: '50%',
  ROUND_SQUARE: '10px',
};

interface ImageType {
  shape: keyof typeof SHAPE;
  size: keyof typeof SIZE;
}

const Img = styled.img<ImageType>`
  width: ${({ size }) => SIZE[size] || SIZE.MEDIUM};
  height: ${({ size }) => SIZE[size] || SIZE.MEDIUM};
  border-radius: ${({ shape }) => SHAPE[shape] || SHAPE.ROUND_SQUARE};
`;

export { Img };
export type { ImageType };
