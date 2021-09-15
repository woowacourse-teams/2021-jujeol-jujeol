export { getRequest, postRequest, deleteRequest, putRequest };

const BEARER = 'Bearer';
const CAN_METHOD_LIST = ['GET', 'POST', 'PUT', 'DELETE'];

function getRequest(url, needToken = false) {
  return apiRequest('GET', url, needToken);
}

async function postRequest(url, formDatas = [{}], needToken = false) {
  const headers = {'Content-Type': 'multipart/form-data'}

  const configure = {
    method: 'POST',
    headers: headers
  };

  const formDataRequests = formDatas.map((drinkRequest) => {
    configure.body = drinkRequest.getFormData();
    return fetch(url, configure);
  });

  try {
    return await Promise.all(formDataRequests);
  } catch (error) {
    return {};
  }
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
    headers: headers,
    credentials: 'include',
  };

  if (body && method.toUpperCase() !== 'GET') {
    configure.body = new FormData();
    configure.body.append('data', body)
  }

  const result = await fetch(url, configure);

  try {
    return await result.json();
  } catch (error) {
    return {};
  }
}
