import { ChangeEventHandler } from 'react';
import notFoundImage from 'src/assets/default.png';

const onImageError: ChangeEventHandler<HTMLImageElement> = (event) => {
  event.target.src = notFoundImage;
};

export { onImageError };
