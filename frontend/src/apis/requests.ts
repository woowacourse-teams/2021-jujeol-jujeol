import axios, { AxiosRequestConfig, Method } from 'axios';

const BASE_URL = process.env.SNOWPACK_PUBLIC_API_URL;

const request = async (config: AxiosRequestConfig) => {
  try {
    const response = await axios(config);

    return response.data;
  } catch ({ response }) {
    throw response.data.error;
  }
};

const API = {
  getDrinks: () => {
    return request({ method: 'get' as Method, url: `${BASE_URL}/drinks` });
  },
  login: <T>(data: T) => {
    return request({ method: 'post' as Method, url: `${BASE_URL}/login/token`, data });
  },
};

export default API;
