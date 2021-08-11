import { LocationDescriptor } from 'history';
import { MemoryRouter as Router } from 'react-router-dom';
import { render } from '@testing-library/react';

import APIProvider from 'src/apis/APIProvider';
import ModalProvider from 'src/components/Modal/ModalProvider';
import { UserProvider } from 'src/contexts/UserContext';

interface Props {
  initialEntries: LocationDescriptor[];
  children: React.ReactNode;
}

const customRender = ({ initialEntries, children }: Props) => {
  const modalPortalRoot = document.createElement('div');

  modalPortalRoot.setAttribute('id', 'modal');
  modalPortalRoot.setAttribute('role', 'dialog');
  document.body.appendChild(modalPortalRoot);

  render(
    <APIProvider>
      <UserProvider>
        <ModalProvider>
          <Router initialEntries={initialEntries}>{children}</Router>
        </ModalProvider>
      </UserProvider>
    </APIProvider>
  );
};

export { customRender };
