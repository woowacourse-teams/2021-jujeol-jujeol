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
    throw response.data;
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
  login: <T>(data: T) => {
    return request({ method: 'POST' as Method, url: REQUEST_URL.LOGIN, data });
  },
  getUserInfo: () => {
    return request({ method: 'GET' as Method, url: REQUEST_URL.GET_USER_INFO });
  },
  getDrinks: () => {
    return request({ method: 'GET' as Method, url: REQUEST_URL.GET_DRINKS });
  },
  getDrink: <T>(id: T) => {
    return request({ method: 'GET' as Method, url: `${REQUEST_URL.GET_DRINK}/${id}` });
  },
  getReview: <T>(id: T, params: URLSearchParams) => {
    return request({ method: 'GET' as Method, url: `/drinks/${id}/reviews?` + params.toString() });
  },
  postReview: <I, D>(id: I, data: D) => {
    return request({ method: 'POST' as Method, url: `/drinks/${id}/reviews`, data });
  },
  editReview: <I, D>(drinkId: I, reviewId: I, data: D) => {
    return request({
      method: 'PUT' as Method,
      url: `/drinks/${drinkId}/reviews/${reviewId}`,
      data,
    });
  },
  deleteReview: <I>(drinkId: I, reviewId: I) => {
    return request({ method: 'DELETE' as Method, url: `/drinks/${drinkId}/reviews/${reviewId}` });
  },
};

export default API;
