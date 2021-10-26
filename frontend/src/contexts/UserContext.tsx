import { createContext, useState } from 'react';
import { useQuery } from 'react-query';

import API from 'src/apis/requests';
import { LOCAL_STORAGE_KEY } from 'src/constants';
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
      onError: () => {
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
