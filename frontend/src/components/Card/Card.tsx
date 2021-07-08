import { Container } from './Card.styles';

interface Props {
  children: React.ReactNode;
}

const Card = ({ children }: Props) => {
  return <Container>{children}</Container>;
};

export default Card;
