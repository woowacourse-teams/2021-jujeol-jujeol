import { Container } from './Header.styles';

interface Props {
  children: React.ReactNode;
}

const Header = ({ children }: Props) => {
  return <Container>{children}</Container>;
};

export default Header;
