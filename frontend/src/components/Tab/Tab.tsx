import { useEffect, useState } from 'react';
import { useContext } from 'react';
import { NavLink } from 'react-router-dom';
import { COLOR, PATH } from 'src/constants';
import UserContext from 'src/contexts/UserContext';
import { HomeIcon, HumanIcon, LoginIcon, SearchIconForTab, StarIcon } from '../@shared/Icons';
import { Nav } from './Tab.styles';

const tabConfig = (isLoggedIn: boolean) => {
  const defaultConfig = [
    {
      mainPath: PATH.HOME,
      path: [PATH.ROOT, PATH.HOME],
      title: '홈',
      Icon: HomeIcon,
    },
    {
      mainPath: PATH.PREFERENCE,
      path: [PATH.PREFERENCE],
      title: '선호도 평가',
      Icon: StarIcon,
    },
    {
      mainPath: PATH.SEARCH,
      path: [PATH.SEARCH],
      title: '검색',
      Icon: SearchIconForTab,
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

  const initWindowHeight = window.innerHeight;
  const [keyboardUp, setKeyboardUp] = useState(false);

  useEffect(() => {
    const checkResizeWindow = () => {
      if (initWindowHeight > window.visualViewport.height) {
        setKeyboardUp(true);
      } else {
        setKeyboardUp(false);
      }
    };

    window.visualViewport.addEventListener('resize', checkResizeWindow);

    return () => window.visualViewport.removeEventListener('resize', checkResizeWindow);
  });

  return (
    <Nav keyboardUp={keyboardUp}>
      <ul>
        {tabConfig(isLoggedIn).map(({ mainPath, path, title, Icon }) => (
          <li key={title}>
            <NavLink exact to={mainPath} isActive={() => path.includes(location.pathname)}>
              <Icon />
              <p>{title}</p>
            </NavLink>
          </li>
        ))}
      </ul>
    </Nav>
  );
};

export default Tab;
