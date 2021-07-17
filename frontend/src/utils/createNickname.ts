import { getLocalStorageItem, setLocalStorageItem } from './localStorage';

const randomBaseName = [
  '서니는나야',
  '티케타카',
  '웨지감자',
  '소롱대롱',
  '피카츄라이츄',
  '크로플맛있따',
  '나봄은스프링',
];

const generateId = (() => {
  let id = 123;

  const getId = () => {
    return id++;
  };

  return getId;
})();

const getRandomBaseName = () => {
  const index = Math.floor(Math.random() * randomBaseName.length);

  return randomBaseName[index];
};

const nicknameGenerator = (() => {
  const nicknameArray: { [key: string]: string } = getLocalStorageItem('jujeol_nicknames') ?? {};

  const getName = (id: string) => {
    if (Object.keys(nicknameArray).includes(id)) {
      return nicknameArray[id];
    }

    nicknameArray[id] = getRandomBaseName() + '_' + generateId();
    setLocalStorageItem('jujeol_nicknames', nicknameArray);

    return nicknameArray[id];
  };

  return getName;
})();

export default nicknameGenerator;
