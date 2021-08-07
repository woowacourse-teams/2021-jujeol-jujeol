import { TouchEventHandler, MouseEventHandler, useState } from 'react';
import { Container, Content, CloseButton } from './Modal.styles';

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
  const [isTouchDown, setIsTouchDown] = useState(false);

  const onClose: MouseEventHandler<HTMLDivElement> = (event) => {
    if (event.currentTarget === event.target) {
      setIsOpened(false);
    }
  };

  const onModalCloseButton = () => {
    setIsOpened(false);
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
    setIsTouchDown(true);
    cancelAnimationFrame(modalPosition.requestAnimationFrameKey);
  };

  const onTouchEnd: TouchEventHandler<HTMLDivElement> = (event) => {
    setIsTouchDown(false);
    event.currentTarget.style.transform = 'none';
  };

  return (
    <Container isOpened={isOpened} onClick={onClose}>
      <Content
        isOpened={isOpened}
        isTouchDown={isTouchDown}
        onTouchStart={onTouchStart}
        onTouchMove={onTouchMove}
        onTouchEnd={onTouchEnd}
      >
        <CloseButton
          type="button"
          onClick={onModalCloseButton}
          aria-label="모달 닫기 버튼"
          aria-disable="false"
        >
          X
        </CloseButton>
        {children}
      </Content>
    </Container>
  );
};

export default Modal;
