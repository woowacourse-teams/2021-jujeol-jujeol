import axios, { AxiosRequestConfig, Method } from 'axios';
import { LOCAL_STORAGE_KEY, REQUEST_URL } from 'src/constants';
import { getLocalStorageItem } from 'src/utils/localStorage';

axios.defaults.baseURL = process.env.SNOWPACK_PUBLIC_API_URL;

const isNoAuthorizationRequired = (path: string) => {
  return [REQUEST_URL.LOGIN, REQUEST_URL.GET_DRINKS].includes(path);
};

const request = async (config: AxiosRequestConfig) => {
  try {
    const response = await axios(config);

    return response.data;
  } catch ({ response }) {
    throw response.data.error;
  }
};

axios.interceptors.request.use(
  (config) => {
    if (!config.headers.Authorization || isNoAuthorizationRequired(config.url as string)) {
      const token = getLocalStorageItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);

      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }

    return config;
  },
  (error) => Promise.reject(error)
);

const API = {
  getDrinks: () => {
    return request({ method: 'GET' as Method, url: REQUEST_URL.GET_DRINKS });
  },
  login: <T>(data: T) => {
    return request({ method: 'POST' as Method, url: REQUEST_URL.LOGIN, data });
  },
  getUserInfo: () => {
    return request({ method: 'GET' as Method, url: REQUEST_URL.GET_USER_INFO });
  },
  getDrink: <T>(id: T) => {
    return request({ method: 'GET' as Method, url: `${REQUEST_URL.GET_DRINK}/${id}` });
  },
};

export default API;
