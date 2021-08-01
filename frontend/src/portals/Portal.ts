import ReactDOM from 'react-dom';

const Portal = ({ id, children }: { id: string; children: React.ReactNode }) => {
  const modalElement = document.getElementById(id);

  return ReactDOM.createPortal(children, modalElement as HTMLElement);
};

export default Portal;
