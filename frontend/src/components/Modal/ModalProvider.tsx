import React, { createContext, useState } from 'react';

import { PORTAL_ID } from 'src/constants';
import Portal from 'src/portals/Portal';
import Modal from './Modal';

interface modalContextValue {
  isModalOpened: boolean;
  openModal: (value: React.ReactNode) => void;
  closeModal: () => void;
}

const modalContext = createContext<modalContextValue | null>(null);

const ModalProvider = ({ children }: { children: React.ReactNode }) => {
  const [content, setContent] = useState<React.ReactNode>();
  const [isModalOpened, setIsModalOpened] = useState(false);

  const openModal = (value: React.ReactNode) => {
    setContent(value);
    setIsModalOpened(true);
  };

  const closeModal = () => {
    setIsModalOpened(false);
  };

  return (
    <modalContext.Provider value={{ isModalOpened, openModal, closeModal }}>
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
