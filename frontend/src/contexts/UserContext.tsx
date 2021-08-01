import { createContext, useState } from 'react';
import { useQuery, useQueryClient } from 'react-query';
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
  const queryClient = useQueryClient();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState<UserData | null>(null);

  const query = useQuery('user-info', API.getUserInfo, {
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

  const getUser = () => {
    queryClient.invalidateQueries('user-info');
  };

  return (
    <UserContext.Provider value={{ isLoggedIn, getUser, userData }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserContext;
export { UserProvider };
