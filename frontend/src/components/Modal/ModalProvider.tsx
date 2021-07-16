import React, { createContext, useState } from 'react';
import Modal from './Modal';
import Portal from 'src/portals/Portal';
import { PORTAL_ID } from 'src/constants';

interface modalContextValue {
  openModal: (value: React.ReactNode) => void;
}

const modalContext = createContext<modalContextValue | null>(null);

const ModalProvider = ({ children }: { children: React.ReactNode }) => {
  const [content, setContent] = useState<React.ReactNode>();
  const [isModalOpened, setIsModalOpened] = useState(false);

  const openModal = (value: React.ReactNode) => {
    setContent(value);
    setIsModalOpened(true);
  };

  return (
    <modalContext.Provider value={{ openModal }}>
      {children}
      <Portal id={PORTAL_ID.MODAL}>
        <Modal isOpened={isModalOpened} setIsOpened={setIsModalOpened}>
          {content}
        </Modal>
      </Portal>
    </modalContext.Provider>
  );
};

export default ModalProvider;
export { modalContext };
