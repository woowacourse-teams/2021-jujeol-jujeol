import { COLOR } from 'src/constants';
import GoBackButton from '../@shared/Button/GoBackButton';
import { NavHeader } from './NavigationHeader.styles';

interface Props {
  title: string;
}

const NavigationHeader = ({ title }: Props) => {
  return (
    <NavHeader>
      <GoBackButton color={COLOR.WHITE} />
      <h2>{title}</h2>
    </NavHeader>
  );
};

export default NavigationHeader;
