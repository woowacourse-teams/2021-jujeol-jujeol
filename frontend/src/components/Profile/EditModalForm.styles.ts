import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Form = styled.form`
  width: 100%;

  label {
    width: 100%;
    margin: 1rem 0 1.2rem;
    padding-left: 0.5rem;

    ${Flex({ flexDirection: 'column' })}

    font-size: 1rem;

    input,
    textarea {
      padding: 0.5rem;
      margin: 0.5rem 0;

      background-color: ${COLOR.GRAY_200}22;
    }

    p {
      padding-left: 0.4rem;

      font-size: 0.7rem;
      color: ${COLOR.GRAY_800};
    }
  }
`;

const NicknameInput = styled.input`
  border: none;
  border-bottom: 1px solid ${COLOR.GRAY_700};

  font-size: 0.9rem;
`;

const BioInput = styled.textarea`
  width: 100%;
  height: 3.5rem;
  overflow-y: auto;

  resize: none;

  border: 1px solid ${COLOR.GRAY_700};
  border-radius: 0.5rem;

  font-size: 0.8rem;
`;

export { BioInput, Form, NicknameInput };
