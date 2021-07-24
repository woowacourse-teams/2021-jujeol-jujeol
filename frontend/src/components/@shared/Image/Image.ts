import styled from '@emotion/styled';

const SIZE = {
  X_SMALL: '4.5rem',
  SMALL: '5.5rem',
  MEDIUM: '6.5rem',
  LARGE: '7rem',
  X_LARGE: '8.5rem',
};

type ImageSizeType = keyof typeof SIZE;

const SHAPE = {
  CIRCLE: '50%',
  ROUND_SQUARE: '10px',
};

type ImageShapeType = keyof typeof SHAPE;

interface ImageType {
  shape: ImageShapeType;
  size: ImageSizeType;
}

const Img = styled.img<ImageType>`
  width: ${({ size }) => SIZE[size] || SIZE.MEDIUM};
  height: ${({ size }) => SIZE[size] || SIZE.MEDIUM};
  border-radius: ${({ shape }) => SHAPE[shape] || SHAPE.ROUND_SQUARE};
`;

export { Img };
export type { ImageType, ImageSizeType, ImageShapeType };
