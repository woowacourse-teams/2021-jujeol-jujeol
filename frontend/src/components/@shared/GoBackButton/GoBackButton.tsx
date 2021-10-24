import { useHistory } from 'react-router-dom';

import ArrowIcon from '../../@Icons/ArrowIcon';
import IconButton from '../Button/IconButton';

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
