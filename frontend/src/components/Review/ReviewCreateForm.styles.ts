import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Form = styled.form`
  display: flex;
  flex-direction: column;
  text-align: left;
  padding-bottom: 1.5rem;
  position: relative;

  p {
    font-size: 0.7rem;
    color: ${COLOR.GRAY_300};
    line-height: 1.5;
    text-align: end;
    margin-bottom: 0.5rem;
  }

  textarea {
    width: 100%;
    height: 4rem;
    resize: none;
    background-color: transparent;
    border: none;

    :focus {
      border: 1px solid ${COLOR.GRAY_200};
    }
  }

  button {
    width: 100%;
    position: absolute;
    left: 0;
    bottom: 1rem;
    height: 2rem;
    background-color: ${COLOR.YELLOW_300};
    border: none;
    border-bottom-left-radius: 0.75rem;
    border-bottom-right-radius: 0.75rem;

    :focus {
      filter: brightness(0.9);
    }
  }
`;

export { Form };
