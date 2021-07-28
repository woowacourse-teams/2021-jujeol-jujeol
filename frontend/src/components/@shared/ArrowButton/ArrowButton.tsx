import { ArrowButtonStyle, Button } from './ArrowButton.styles';

interface Props extends ArrowButtonStyle {
  children?: React.ReactNode;
  onClick?: () => void;
}

const ArrowButton = ({ fontSize, size, borderWidth, dir, onClick, children }: Props) => {
  return (
    <Button
      type="button"
      fontSize={fontSize}
      size={size}
      borderWidth={borderWidth}
      dir={dir}
      onClick={onClick}
    >
      {children}
    </Button>
  );
};

export default ArrowButton;
