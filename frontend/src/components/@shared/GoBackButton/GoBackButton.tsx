import { useHistory } from 'react-router-dom';
import Arrow from '../Arrow/Arrow';
import { Button } from './GoBackButton.styles';

const GoBackButton = ({ color }: { color?: string }) => {
  const history = useHistory();

  const onMoveToPrevPage = () => {
    history.goBack();
  };

  return (
    <Button type="button" onClick={onMoveToPrevPage}>
      <Arrow size="0.7rem" borderWidth="0.125rem" dir="LEFT" color={color} />
    </Button>
  );
};

export default GoBackButton;
