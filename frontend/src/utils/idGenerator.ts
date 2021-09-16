const idGenerator = (() => {
  let id = 0;

  const increase = () => {
    id = id + 1;
  };

  const getId = () => {
    increase();
    return id;
  };

  return getId;
})();

export default idGenerator;
