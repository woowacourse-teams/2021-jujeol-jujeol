import { createContext, useState } from 'react';
import { useQuery, useQueryClient } from 'react-query';
import API from 'src/apis/requests';
import { LOCAL_STORAGE_KEY } from 'src/constants';
import { removeLocalStorageItem } from 'src/utils/localStorage';

const UserContext = createContext({
  isLoggedIn: false,
  getUser: () => {
    return;
  },
});

const UserProvider = ({ children }: { children: React.ReactNode }) => {
  const queryClient = useQueryClient();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const query = useQuery('user-info', API.getUserInfo, {
    retry: 0,
    onSuccess: () => setIsLoggedIn(true),
    onError: () => {
      removeLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
      setIsLoggedIn(false);
    },
  });

  const getUser = () => {
    queryClient.invalidateQueries('user-info');
  };

  return <UserContext.Provider value={{ isLoggedIn, getUser }}>{children}</UserContext.Provider>;
};

export default UserContext;
export { UserProvider };
