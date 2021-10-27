import { createContext, useState } from 'react';
import { useQuery } from 'react-query';
import { useHistory } from 'react-router-dom';

import API from 'src/apis/requests';
import { APPLICATION_ERROR_CODE, LOCAL_STORAGE_KEY, PATH } from 'src/constants';
import QUERY_KEY from 'src/constants/queryKey';

type UserData = {
  id: number;
  nickname: string;
  bio: string;
};

interface UserContext {
  isLoggedIn: boolean;
  setIsLoggedIn: (state: boolean) => void;
  getUser: () => void;
  userData: UserData | null;
}

const UserContext = createContext<UserContext>({
  isLoggedIn: false,
  setIsLoggedIn: (state) => {
    return state;
  },
  getUser: () => {
    return;
  },
  userData: null,
});

const UserProvider = ({ children }: { children: React.ReactNode }) => {
  const history = useHistory();

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState<UserData | null>(null);

  const { refetch } = useQuery(
    QUERY_KEY.USER,
    () => {
      if (localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN)) {
        return API.getUserInfo();
      }
    },
    {
      retry: 0,
      onSuccess: (response) => {
        if (response?.data) {
          setIsLoggedIn(true);
          setUserData(response?.data);
        }
      },
      onError: (error: Request.Error) => {
        if (
          error.code === APPLICATION_ERROR_CODE.NETWORK_ERROR ||
          error.code === APPLICATION_ERROR_CODE.INTERNAL_SERVER_ERROR
        ) {
          history.push({
            pathname: PATH.ERROR_PAGE,
            state: { code: error.code },
          });
          return;
        }

        localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
        setIsLoggedIn(false);
      },
    }
  );

  const getUser = async () => {
    await refetch();
  };

  return (
    <UserContext.Provider value={{ isLoggedIn, getUser, userData, setIsLoggedIn }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserContext;
export { UserProvider };
