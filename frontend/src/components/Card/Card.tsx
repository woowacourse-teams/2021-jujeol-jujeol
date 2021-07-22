import { Container } from './Card.styles';

interface Props extends React.CSSProperties {
  children: React.ReactNode;
}

const Card = ({ width = '100%', height, padding, backgroundColor, border, children }: Props) => {
  return (
    <Container
      width={width}
      height={height}
      padding={padding}
      backgroundColor={backgroundColor}
      border={border}
    >
      {children}
    </Container>
  );
};

export default Card;
