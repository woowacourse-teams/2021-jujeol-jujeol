import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Wrapper = styled.div<{ isOpened: boolean }>`
  visibility: ${({ isOpened }) => (isOpened ? 'visible' : 'hidden')};
  ${Flex({ justifyContent: 'center', alignItems: 'center' })}
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  background-color: ${COLOR.BLACK}cc;
`;

const Content = styled.div`
  width: 100%;
  min-height: 25vh;
  padding: 0 2rem 2rem;

  ${Flex({ justifyContent: 'center', alignItems: 'center', flexDirection: 'column' })}
  position: relative;

  line-break: anywhere;

  p {
    font-size: 0.9rem;
  }
`;

const ButtonWrapper = styled.div`
  height: 2.5rem;

  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;

  border-bottom-left-radius: 1rem;
  border-bottom-right-radius: 1rem;

  button {
    width: 50%;
    height: 100%;

    border: inherit;
    background-color: transparent;

    color: ${COLOR.GRAY_700};

    :active {
      background-color: ${COLOR.PURPLE_100};

      color: ${COLOR.PURPLE_700};
    }
  }

  button:first-child {
    padding-left: 1rem;

    border-bottom-left-radius: inherit;
  }

  button:last-child {
    padding-right: 1rem;

    border-bottom-right-radius: inherit;
  }

  :before {
    content: ' ';

    width: 100%;
    height: 0;

    position: absolute;
    top: 0;
    left: 0;

    border-top: 1px solid ${COLOR.GRAY_200};
  }

  :after {
    content: ' ';

    width: 0;
    height: 60%;

    position: absolute;
    bottom: 20%;
    left: 50%;

    border-left: 1px solid ${COLOR.GRAY_200};
  }
`;

export { ButtonWrapper, Content, Wrapper };
