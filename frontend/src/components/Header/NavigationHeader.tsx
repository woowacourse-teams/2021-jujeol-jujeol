import { COLOR } from 'src/constants';
import { hiddenStyle } from 'src/styles/hidden';
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
      <Heading.level1 css={hiddenStyle}>주절주절</Heading.level1>
      <Heading.level2>{title}</Heading.level2>
    </NavHeader>
  );
};

export default NavigationHeader;
