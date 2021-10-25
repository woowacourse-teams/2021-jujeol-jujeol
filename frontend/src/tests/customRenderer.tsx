import { MemoryRouter as Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import { LocationDescriptor } from 'history';

import APIProvider from 'src/apis/APIProvider';
import SnackbarProvider from 'src/components/@shared/Snackbar/SnackbarProvider';
import ConfirmProvider from 'src/components/Confirm/ConfirmProvider';
import ModalProvider from 'src/components/Modal/ModalProvider';
import { PORTAL_ID } from 'src/constants';
import { UserProvider } from 'src/contexts/UserContext';

interface Props {
  initialEntries: LocationDescriptor[];
  children: React.ReactNode;
}

const customRender = ({ initialEntries, children }: Props) => {
  const modalPortalRoot = document.createElement('div');
  const confirmPortalRoot = document.createElement('div');
  const snackbarPortalRoot = document.createElement('div');

  modalPortalRoot.setAttribute('id', PORTAL_ID.MODAL);
  modalPortalRoot.setAttribute('role', 'dialog');
  confirmPortalRoot.setAttribute('id', PORTAL_ID.CONFIRM);
  snackbarPortalRoot.setAttribute('id', PORTAL_ID.SNACKBAR);

  document.body.appendChild(modalPortalRoot);
  document.body.appendChild(confirmPortalRoot);
  document.body.appendChild(snackbarPortalRoot);

  render(
    <APIProvider>
      <UserProvider>
        <ConfirmProvider>
          <SnackbarProvider>
            <ModalProvider>
              <Router initialEntries={initialEntries}>{children}</Router>
            </ModalProvider>
          </SnackbarProvider>
        </ConfirmProvider>
      </UserProvider>
    </APIProvider>
  );
};

export { customRender };
