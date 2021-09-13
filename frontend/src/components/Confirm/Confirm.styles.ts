import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Wrapper = styled.div<{ isOpened: boolean }>`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  background-color: ${COLOR.BLACK_900 + 'ee'};
  visibility: ${({ isOpened }) => (isOpened ? 'visible' : 'hidden')};
  ${Flex({ justifyContent: 'center', alignItems: 'center' })}
`;

const Content = styled.div`
  width: 100%;
  min-height: 25vh;
  padding: 0 2rem 2rem;

  position: relative;
  line-break: anywhere;

  ${Flex({ justifyContent: 'center', alignItems: 'center', flexDirection: 'column' })}

  h3 {
    font-weight: bold;
    font-size: 1rem;
    margin-bottom: 1rem;
  }

  p {
    font-size: 0.9rem;
  }
`;

const ButtonWrapper = styled.div`
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;

  width: 100%;
  height: 2.5rem;

  border-bottom-left-radius: 1rem;
  border-bottom-right-radius: 1rem;

  button {
    width: 50%;
    height: 100%;
    border: inherit;
    background-color: transparent;
    color: ${COLOR.GRAY_300};

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
    width: 100%;
    height: 0;
    border-top: 1px solid ${COLOR.GRAY_200};
    content: ' ';
    position: absolute;
    top: 0;
    left: 0;
  }

  :after {
    content: ' ';
    position: absolute;
    bottom: 20%;
    left: 50%;
    width: 0;
    height: 60%;
    border-left: 1px solid ${COLOR.GRAY_200};
  }
`;

export { Wrapper, Content, ButtonWrapper };
