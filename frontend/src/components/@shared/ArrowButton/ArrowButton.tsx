import { ArrowButtonStyle, Button } from './ArrowButton.styles';

interface Props extends ArrowButtonStyle {
  name?: string;
  onClick?: () => void;
}

const ArrowButton = ({ name, fontSize, size, borderWidth, dir, onClick }: Props) => {
  return (
    <Button fontSize={fontSize} size={size} borderWidth={borderWidth} dir={dir} onClick={onClick}>
      {name ?? ''}
    </Button>
  );
};

export default ArrowButton;
