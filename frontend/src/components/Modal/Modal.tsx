import { Container, Content } from './Modal.styles';

interface Props {
  children: React.ReactNode;
}

const Modal = ({ children }: Props) => {
  return (
    <Container role="dialog">
      <Content>{children}</Content>
    </Container>
  );
};

export default Modal;
