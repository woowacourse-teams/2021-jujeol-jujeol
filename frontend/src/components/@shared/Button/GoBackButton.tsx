import { useHistory } from 'react-router-dom';

import IconButton from './IconButton';
import ArrowIcon from '../Icons/ArrowIcon';

const GoBackButton = ({ color }: { color?: string }) => {
  const history = useHistory();

  const onMoveToPrevPage = () => {
    history.goBack();
  };

  return (
    <IconButton
      type="button"
      size="X_SMALL"
      backgroundColor="transparent"
      onClick={onMoveToPrevPage}
    >
      <ArrowIcon color={color} />
    </IconButton>
  );
};

export default GoBackButton;
