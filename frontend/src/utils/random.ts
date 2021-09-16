const getRandomNumbers = ({ min, max, count }: { min: number; max: number; count: number }) => {
  const numbers: number[] = [];

  while (numbers.length < count) {
    const number = Math.floor(Math.random() * (max - min) + min);

    if (!numbers.includes(number)) {
      numbers.push(number);
    }
  }

  return numbers;
};

export { getRandomNumbers };
