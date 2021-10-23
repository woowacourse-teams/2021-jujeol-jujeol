import { LocationDescriptor } from 'history';
import { MemoryRouter as Router } from 'react-router-dom';
import { render } from '@testing-library/react';

import APIProvider from 'src/apis/APIProvider';
import ModalProvider from 'src/components/Modal/ModalProvider';
import { UserProvider } from 'src/contexts/UserContext';
import ConfirmProvider from 'src/components/Confirm/ConfirmProvider';

interface Props {
  initialEntries: LocationDescriptor[];
  children: React.ReactNode;
}

const customRender = ({ initialEntries, children }: Props) => {
  const modalPortalRoot = document.createElement('div');
  const confirmPortalRoot = document.createElement('div');

  modalPortalRoot.setAttribute('id', 'modal');
  modalPortalRoot.setAttribute('role', 'dialog');
  confirmPortalRoot.setAttribute('id', 'confirm');
  document.body.appendChild(modalPortalRoot);
  document.body.appendChild(confirmPortalRoot);

  render(
    <APIProvider>
      <UserProvider>
        <ConfirmProvider>
          <ModalProvider>
            <Router initialEntries={initialEntries}>{children}</Router>
          </ModalProvider>
        </ConfirmProvider>
      </UserProvider>
    </APIProvider>
  );
};

export { customRender };
