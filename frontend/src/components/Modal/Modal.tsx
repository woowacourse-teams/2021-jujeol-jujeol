import { Container, Content } from './Modal.styles';

interface Props {
  isOpened: boolean;
  setIsOpened: React.Dispatch<React.SetStateAction<boolean>>;
  children: React.ReactNode;
}

const Modal = ({ isOpened, setIsOpened, children }: Props) => {
  const onClose = () => {
    setIsOpened(false);
  };

  return (
    <Container isOpened={isOpened}>
      <Content isOpened={isOpened} onClick={onClose}>
        {children}
      </Content>
    </Container>
  );
};

export default Modal;
