import { NavLink } from 'react-router-dom';
import { COLOR, PATH } from 'src/constants';
import HomeIcon from '../Icons/home';
import HumanIcon from '../Icons/human';
import { Nav } from './Tab.styles';

const tabConfig = [
  {
    mainPath: PATH.HOME,
    path: [PATH.ROOT, PATH.HOME],
    title: 'Home',
    Icon: HomeIcon,
  },
  {
    mainPath: PATH.LOGIN,
    path: [PATH.LOGIN],
    title: 'Login',
    Icon: HumanIcon,
  },
];

const Tab = () => {
  return (
    <Nav>
      <ul>
        {tabConfig.map(({ mainPath, path, title, Icon }) => (
          <li key={title}>
            <NavLink exact to={mainPath} isActive={() => path.includes(location.pathname)}>
              <Icon color={COLOR.BLACK_900} />
              <p>{title}</p>
            </NavLink>
          </li>
        ))}
      </ul>
    </Nav>
  );
};

export default Tab;
