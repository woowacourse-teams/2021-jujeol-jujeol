import { TouchEventHandler, MouseEventHandler } from 'react';
import { Container, Content } from './Modal.styles';

interface Props {
  isOpened: boolean;
  setIsOpened: React.Dispatch<React.SetStateAction<boolean>>;
  children: React.ReactNode;
}

const modalPosition = {
  contentHeight: window.innerHeight * 0.8,
  currentYPosition: 0,
  requestAnimationFrameKey: 0,
};

const Modal = ({ isOpened, setIsOpened, children }: Props) => {
  const onClose: MouseEventHandler<HTMLDivElement> = (event) => {
    if (event.currentTarget === event.target) {
      setIsOpened(false);
    }
  };

  const onTouchStart: TouchEventHandler<HTMLDivElement> = (event) => {
    modalPosition.currentYPosition = event.touches[0].clientY;
  };

  const onTouchMove: TouchEventHandler<HTMLDivElement> = (event) => {
    const contentStyle = event.currentTarget.style;
    const changeYPosition = event.touches[0].clientY - modalPosition.currentYPosition;

    if (changeYPosition > 0) {
      modalPosition.requestAnimationFrameKey = requestAnimationFrame(() => {
        contentStyle.transform = `translateY(${changeYPosition}px)`;
      });
    }

    if (changeYPosition > modalPosition.contentHeight * 0.5) {
      closeModalByTouch();
    }
  };

  const closeModalByTouch = () => {
    setIsOpened(false);
    cancelAnimationFrame(modalPosition.requestAnimationFrameKey);
  };

  const onTouchEnd: TouchEventHandler<HTMLDivElement> = (event) => {
    event.currentTarget.style.transform = 'none';
  };

  return (
    <Container isOpened={isOpened} onClick={onClose}>
      <Content
        isOpened={isOpened}
        onTouchStart={onTouchStart}
        onTouchMove={onTouchMove}
        onTouchEnd={onTouchEnd}
      >
        {children}
      </Content>
    </Container>
  );
};

export default Modal;
