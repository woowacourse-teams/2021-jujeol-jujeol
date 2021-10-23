import { SnackbarContent } from './SnackbarProvider';
import { MESSAGE_TYPE_EMOJI } from 'src/constants';
import { Container } from './Snackbar.styles';

const Snackbar = ({ type, message }: SnackbarContent) => {
  return (
    <Container message={!!message}>
      <span>{type && MESSAGE_TYPE_EMOJI[type]}</span>
      <p aria-live="assertive">{message}</p>
    </Container>
  );
};

export default Snackbar;
