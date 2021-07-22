import React from 'react';
import { ImageType, shapeType, sizeType, Img } from './Image.styles';

interface Props extends ImageType {
  shape: shapeType;
  size: sizeType;
}

const Image = ({ src, alt, size, shape }: Props) => {
  return <Img src={src} alt={alt} shape={shape} size={size} />;
};

export default Image;
