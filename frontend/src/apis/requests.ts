import axios, { AxiosRequestConfig, Method } from 'axios';

const BASE_URL = 'http://localhost:8080';

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
};

export default API;
