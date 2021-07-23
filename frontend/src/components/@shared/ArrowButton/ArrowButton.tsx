import { ArrowButtonStyle, Button } from './ArrowButton.styles';

interface Props extends ArrowButtonStyle {
  name?: string;
  onClick?: () => void;
}

const ArrowButton = ({ name, fontSize, size, borderSize, dir, onClick }: Props) => {
  return (
    <Button fontSize={fontSize} size={size} borderSize={borderSize} dir={dir} onClick={onClick}>
      {name ?? ''}
    </Button>
  );
};

export default ArrowButton;
