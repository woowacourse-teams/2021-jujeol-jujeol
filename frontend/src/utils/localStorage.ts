const getLocalStorageItem = (key: string) => {
  try {
    const item = JSON.parse(localStorage.getItem(key) as string);

    return item;
  } catch (error) {
    return '';
  }
};

const setLocalStorageItem = <T>(key: string, item: T) => {
  try {
    localStorage.setItem(key, JSON.stringify(item));
  } catch (error) {
    console.error(error);
  }
};

const removeLocalStorageItem = (key: string) => {
  localStorage.removeItem(key);
};

export { getLocalStorageItem, removeLocalStorageItem, setLocalStorageItem };
