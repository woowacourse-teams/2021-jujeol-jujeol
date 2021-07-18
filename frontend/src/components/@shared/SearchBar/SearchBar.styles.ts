import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Container = styled.form`
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.5rem 2rem;
  width: 80%;
  height: 3rem;
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 2rem;
  border: 2px solid white;
  position: relative;

  input {
    width: 100%;
    height: 100%;
    color: ${COLOR.WHITE_100};
    border: 0;
    outline: none;
    background-color: transparent;
    font-size: 1rem;
    padding: 0.2rem 0.2rem;
  }

  input::placeholder {
    font-size: 1rem;
    color: ${COLOR.GRAY_200};
  }

  button {
    width: 2rem;
    height: 2rem;
    border-radius: 1rem;
    border: 0;
    background-color: ${COLOR.GRAY_200};

    position: absolute;
    right: 0.3rem;
    top: 50%;
    transform: translateY(-50%);
  }
`;

export { Container };
