import { API_URL } from './apiUrl.js';

export { getRequest, postRequest, deleteRequest, putRequest, postRequestWithFormData, putRequestWithFormData };

const BEARER = 'Bearer';
const CAN_METHOD_LIST = ['GET', 'POST', 'PUT', 'DELETE'];

function getRequest(url, needToken = false) {
  return apiRequest('GET', url, needToken);
}

async function apiRequestWithFormData(method, url, formDatas = [{}], needToken = false) {
  const configure = {
    method: method
  };

  const formDataRequests = formDatas.map((drinkRequest) => {
    configure.body = drinkRequest.getFormData();
    return fetch(API_URL + url, configure);
  });

  try {
    return await Promise.all(formDataRequests);
  } catch (error) {
    return {};
  }
}

function postRequestWithFormData(url, formDatas = [{}], needToken = false) {
  return apiRequestWithFormData('POST', url, formDatas, needToken);
}

function putRequestWithFormData(url, formDatas = [{}], needToken = false) {
  return apiRequestWithFormData('PUT', url, formDatas, needToken);
}

function postRequest(url, needToken = false) {
  return apiRequest('POST', url, needToken);
}

function deleteRequest(url, needToken = false) {
  return apiRequest('DELETE', url, needToken);
}

function putRequest(url, body = {}, needToken = false) {
  return apiRequest('PUT', url, needToken, body);
}

async function apiRequest(method, url = '', needToken = false, body = {}) {
  const isMethod = CAN_METHOD_LIST.findIndex((canMethod) => canMethod === method.toUpperCase()) !== -1;
  if (!method || !isMethod) {
    throw new Error(`not valid method : ${method} / can method : get, post, delete, put`);
  }

  const headers = method === 'POST' ? { 'Content-Type': 'multipart/form-data'} : { 'Content-Type': 'application/json' }
  if (needToken) {
    headers.Authorization = `${BEARER} ${localStorage.getItem('accessToken')}`;
  }

  const configure = {
    method: method,
    headers: headers
  };

  if (body && method.toUpperCase() !== 'GET') {
    configure.body = new FormData();
    configure.body.append('data', body)
  }

  const result = await fetch(API_URL + url, configure);

  try {
    return await result.json();
  } catch (error) {
    return {};
  }
}
