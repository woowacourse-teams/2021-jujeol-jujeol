import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Form = styled.form`
  height: 75%;
  width: 100%;

  ${Flex({ flexDirection: 'column', justifyContent: 'space-between', alignItems: 'center' })}

  button {
    padding: 0.4rem 0;
  }
`;

const Content = styled.div`
  width: 100%;
  height: 70%;
  margin: 2rem 0 1.5rem;

  flex-grow: 1;
  position: relative;

  div {
    width: 100%;
    padding: 0 0.3rem;

    ${Flex({ justifyContent: 'space-between' })}
    position: absolute;
    top: -1rem;
    right: 0;

    font-size: 0.75rem;
    color: ${COLOR.GRAY_300};
  }

  textarea {
    width: 100%;
    height: 100%;
    margin-top: 0.5rem;
    padding: 1rem;

    border: none;
    border-radius: 12px;
    background-color: ${COLOR.GRAY_100};

    font-size: 1rem;

    resize: none;
  }
`;

export { Content, Form };
