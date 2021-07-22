import styled from '@emotion/styled';
import { ImgHTMLAttributes } from 'react';

const SIZE = {
  SMALL: '4.5rem',
  MEDIUM: '6.25rem',
  LARGE: '7.5rem',
};

type sizeType = keyof typeof SIZE;

const SHAPE = {
  CIRCLE: '50%',
  ROUND_SQUARE: '10px',
};
type shapeType = keyof typeof SHAPE;

interface ImageType extends ImgHTMLAttributes<HTMLImageElement> {
  shape: shapeType;
  size: sizeType;
}

const Img = styled.img<ImageType>`
  width: ${({ size }) => SIZE[size] || SIZE.MEDIUM};
  height: ${({ size }) => SIZE[size] || SIZE.MEDIUM};
  border-radius: ${({ shape }) => SHAPE[shape] || SHAPE.ROUND_SQUARE};
`;

export { Img };
export type { ImageType, sizeType, shapeType };
