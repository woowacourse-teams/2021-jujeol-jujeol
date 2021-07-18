import { MouseEventHandler } from 'react';
import { Container, Content } from './Modal.styles';

interface Props {
  isOpened: boolean;
  setIsOpened: React.Dispatch<React.SetStateAction<boolean>>;
  children: React.ReactNode;
}

const Modal = ({ isOpened, setIsOpened, children }: Props) => {
  const onClose: MouseEventHandler<HTMLDivElement> = (event) => {
    if (event.currentTarget === event.target) {
      setIsOpened(false);
    }
  };

  return (
    <Container isOpened={isOpened} onClick={onClose}>
      <Content isOpened={isOpened}>{children}</Content>
    </Container>
  );
};

export default Modal;
