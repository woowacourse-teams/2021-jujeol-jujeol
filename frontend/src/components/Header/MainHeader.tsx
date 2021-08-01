import JujeolLogo from 'src/assets/jujeol_logo';
import Header from '../@shared/Header/Header';
import { Logo } from './MainHeader.styles';

const MainHeader = () => {
  return (
    <Header>
      <Logo>
        <JujeolLogo />
      </Logo>
    </Header>
  );
};

export default MainHeader;
