import { createContext, useState } from 'react';
import { useQuery } from 'react-query';
import API from 'src/apis/requests';
import { LOCAL_STORAGE_KEY } from 'src/constants';
import nicknameGenerator from 'src/utils/createNickname';
import { removeLocalStorageItem } from 'src/utils/localStorage';

type UserData = {
  id: number;
  nickname: string;
  bio: string;
};

interface UserContext {
  isLoggedIn: boolean;
  getUser: () => void;
  userData: UserData | null;
}

const UserContext = createContext<UserContext>({
  isLoggedIn: false,
  getUser: () => {
    return;
  },
  userData: null,
});

const UserProvider = ({ children }: { children: React.ReactNode }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState<UserData | null>(null);

  const { refetch } = useQuery('user-info', API.getUserInfo, {
    retry: 0,
    onSuccess: ({ data }) => {
      setIsLoggedIn(true);
      setUserData({ ...data, name: nicknameGenerator(data.id) });
    },
    onError: () => {
      removeLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
      setIsLoggedIn(false);
    },
  });

  const getUser = async () => {
    await refetch();
  };

  return (
    <UserContext.Provider value={{ isLoggedIn, getUser, userData }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserContext;
export { UserProvider };
