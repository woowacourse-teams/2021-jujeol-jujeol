import { useContext } from 'react';
import { NavLink } from 'react-router-dom';
import { COLOR, PATH } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import { HomeIcon, HumanIcon, LoginIcon } from '../@shared/Icons';

import { Nav } from './Tab.styles';

const tabConfig = (isLoggedIn: boolean) => {
  const defaultConfig = [
    {
      mainPath: PATH.HOME,
      path: [PATH.ROOT, PATH.HOME],
      title: '홈',
      Icon: HomeIcon,
    },
  ];

  return isLoggedIn
    ? defaultConfig.concat([
        {
          mainPath: PATH.MYPAGE,
          path: [PATH.MYPAGE],
          title: '내 정보',
          Icon: HumanIcon,
        },
      ])
    : defaultConfig.concat([
        {
          mainPath: PATH.LOGIN,
          path: [PATH.LOGIN],
          title: '로그인',
          Icon: LoginIcon,
        },
      ]);
};

const Tab = () => {
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  return (
    <Nav>
      <ul>
        {tabConfig(isLoggedIn).map(({ mainPath, path, title, Icon }) => (
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
