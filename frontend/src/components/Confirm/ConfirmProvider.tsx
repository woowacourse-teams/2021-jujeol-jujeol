import React, { createContext, useState } from 'react';
import Portal from 'src/portals/Portal';
import { PORTAL_ID } from 'src/constants';
import Confirm, { ConfirmProps } from './Confirm';

interface confirmContextValue {
  setConfirm: ({
    message,
    subMessage,
    onConfirm,
    onCancel,
  }: Omit<ConfirmProps, 'isOpened'>) => void;
  closeConfirm: () => void;
}

const confirmContext = createContext<confirmContextValue | null>(null);

const ConfirmProvider = ({ children }: { children: React.ReactNode }) => {
  const [content, setContent] = useState<Omit<ConfirmProps, 'isOpened'>>({
    message: '',
    subMessage: '',
    onConfirm: () => {
      return;
    },
    onCancel: () => {
      return;
    },
  });
  const [isOpened, setIsOpened] = useState(false);

  const openConfirm = () => {
    setIsOpened(true);
  };

  const closeConfirm = () => {
    setIsOpened(false);
  };

  const setConfirm = ({
    message,
    subMessage,
    onConfirm,
    onCancel,
  }: Omit<ConfirmProps, 'isOpened'>) => {
    setContent({ message, subMessage, onConfirm, onCancel });
    openConfirm();
  };

  return (
    <confirmContext.Provider value={{ setConfirm, closeConfirm }}>
      {children}
      <Portal id={PORTAL_ID.CONFIRM}>
        {isOpened && (
          <Confirm
            isOpened={isOpened}
            message={content.message}
            subMessage={content.subMessage}
            onConfirm={content.onConfirm}
            onCancel={content.onCancel}
          />
        )}
      </Portal>
    </confirmContext.Provider>
  );
};

export default ConfirmProvider;
export { confirmContext };
