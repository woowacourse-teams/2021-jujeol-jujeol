import { ImageShapeType, ImageSizeType, Img } from '../@shared/Image/Image';
import { Container } from './VerticalItem.styles';

interface Props {
  src: string;
  alt: string;
  shape: ImageShapeType;
  size: ImageSizeType;
  title: string;
  children?: React.ReactNode;

  onClick?: () => void;
}

const VerticalItem = ({ src, alt, shape, size, title, onClick, children }: Props) => {
  return (
    <Container onClick={onClick}>
      <Img src={src} alt={alt} shape={shape} size={size} />
      <p>{title}</p>
      {children}
    </Container>
  );
};

export default VerticalItem;
