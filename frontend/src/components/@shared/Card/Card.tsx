import { Container } from './Card.styles';

interface Props extends React.CSSProperties {
  children: React.ReactNode;
  onClick?: () => void;
}

const Card = ({
  width = '100%',
  height,
  padding,
  color,
  backgroundColor,
  border,
  flexDirection,
  children,
  onClick,
}: Props) => {
  return (
    <Container
      width={width}
      height={height}
      padding={padding}
      color={color}
      backgroundColor={backgroundColor}
      border={border}
      flexDirection={flexDirection}
      onClick={onClick}
    >
      {children}
    </Container>
  );
};

export default Card;
