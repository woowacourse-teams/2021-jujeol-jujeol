import { COLOR } from 'src/constants';
import GoBackButton from '../@shared/Button/GoBackButton';
import Heading from '../@shared/Heading/Heading';
import { NavHeader } from './NavigationHeader.styles';

interface Props {
  title: string;
}

const NavigationHeader = ({ title }: Props) => {
  return (
    <NavHeader>
      <GoBackButton color={COLOR.WHITE} />
      <Heading.level2>{title}</Heading.level2>
    </NavHeader>
  );
};

export default NavigationHeader;
