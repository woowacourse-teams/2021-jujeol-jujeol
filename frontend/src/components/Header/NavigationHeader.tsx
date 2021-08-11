import { useHistory } from 'react-router-dom';
import Arrow from '../@shared/Arrow/Arrow';
import { NavHeader } from './NavigationHeader.styles';

interface Props {
  title: string;
}

const NavigationHeader = ({ title }: Props) => {
  const history = useHistory();
  const onMoveToPrevPage = () => history.goBack();

  return (
    <NavHeader>
      <button type="button" onClick={onMoveToPrevPage}>
        <Arrow size="0.7rem" borderWidth="2px" dir="LEFT" />
      </button>
      <h2>{title}</h2>
    </NavHeader>
  );
};

export default NavigationHeader;
