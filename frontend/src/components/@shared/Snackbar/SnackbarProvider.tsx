import { createContext, useEffect, useState } from 'react';

import { MESSAGE_TYPE_EMOJI, PORTAL_ID } from 'src/constants';
import Portal from 'src/portals/Portal';
import Snackbar from './Snackbar';

export interface SnackbarContent {
  type: keyof typeof MESSAGE_TYPE_EMOJI | null;
  message: string | null;
}

interface SnackbarContextValue {
  setSnackbarMessage: ({ type, message }: SnackbarContent) => void;
}

const SnackbarContext = createContext<SnackbarContextValue | null>(null);

const defaultSnackbarMessage = { type: null, message: null };

const SnackbarProvider = ({ children }: { children: React.ReactNode }) => {
  const [snackbarMessage, setSnackbarMessage] = useState<SnackbarContent>(defaultSnackbarMessage);

  useEffect(() => {
    const snackbarTimer = setTimeout(() => {
      setSnackbarMessage(defaultSnackbarMessage);
    }, 3000);

    return () => clearTimeout(snackbarTimer);
  }, [snackbarMessage]);

  return (
    <SnackbarContext.Provider value={{ setSnackbarMessage }}>
      {children}
      <Portal id={PORTAL_ID.SNACKBAR}>
        <Snackbar type={snackbarMessage.type} message={snackbarMessage.message} />
      </Portal>
    </SnackbarContext.Provider>
  );
};

export default SnackbarProvider;
export { SnackbarContext };
