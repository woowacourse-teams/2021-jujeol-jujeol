import { Container } from './Card.styles';

interface Props extends React.CSSProperties {
  children: React.ReactNode;
  onClick?: () => void;
}

const Card = ({
  width = '100%',
  height,
  padding,
  backgroundColor,
  border,
  children,
  onClick,
}: Props) => {
  return (
    <Container
      width={width}
      height={height}
      padding={padding}
      backgroundColor={backgroundColor}
      border={border}
      onClick={onClick}
    >
      {children}
    </Container>
  );
};

export default Card;
