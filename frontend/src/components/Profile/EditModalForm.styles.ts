import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Form = styled.form`
  width: 100%;

  h2 {
    text-align: center;
    font-size: 1.3rem;
    font-weight: 600;
    margin-bottom: 2rem;
  }

  label {
    ${Flex({ flexDirection: 'column' })}
    width: 100%;
    margin: 1rem 0;
    padding-left: 0.5rem;

    h3 {
      font-size: 1rem;
      font-weight: 600;
    }

    input,
    textarea {
      padding: 0.5rem;
      margin: 0.5rem 0;

      background-color: ${COLOR.GRAY_200}22;
    }
  }
`;

const NicknameInput = styled.input`
  font-size: 1.2rem;

  border: none;
  border-bottom: 1px solid ${COLOR.BLACK};
`;

const BioInput = styled.textarea`
  height: 3.5rem;
  width: 100%;
  overflow-y: auto;
  resize: none;
  border: 1px solid ${COLOR.BLACK};
  border-radius: 0.5rem;

  font-size: 0.9rem;
`;

export { Form, NicknameInput, BioInput };
