import { useEffect, useState } from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import { COLOR, PATH } from 'src/constants';
import HomeIcon from '../Icons/home';
import HumanIcon from '../Icons/human';
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
          Icon: HumanIcon,
        },
      ]);
};

const getAccessToken = () => localStorage.getItem('jujeol_access_token');

const Tab = () => {
  const location = useLocation();
  const [isLoggedIn, setIsLoggedIn] = useState(!!getAccessToken());

  useEffect(() => {
    setIsLoggedIn(!!getAccessToken());
  }, [location.pathname]);

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
