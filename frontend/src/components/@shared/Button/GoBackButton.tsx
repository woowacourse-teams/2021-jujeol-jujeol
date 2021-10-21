import { useHistory } from 'react-router-dom';

import IconButton from './IconButton';
import ArrowIcon from '../../@Icons/ArrowIcon';
import { SerializedStyles } from '@emotion/utils';

const GoBackButton = ({ color, css }: { color?: string; css?: SerializedStyles }) => {
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
      css={css}
    >
      <ArrowIcon color={color} />
    </IconButton>
  );
};

export default GoBackButton;
