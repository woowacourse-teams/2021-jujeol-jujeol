import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Wrapper = styled.div`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: ${COLOR.BLACK_900 + 'ee'};

  ${Flex({ justifyContent: 'center', alignItems: 'center' })}
`;

const Content = styled.div`
  width: 85vw;
  min-height: 15vh;
  padding: 2rem 2rem 2.5rem;

  position: relative;
  line-break: anywhere;

  ${Flex({ justifyContent: 'center', alignItems: 'center', flexDirection: 'column' })}

  h3 {
    font-weight: bold;
    font-size: 1rem;
    margin-bottom: 0.5rem;
  }

  button:last-child {
    position: absolute;
    right: 0;
    bottom: 0;
    padding: 0 1rem;

    height: 2.5rem;
    background-color: transparent;
    border: none;
    color: ${COLOR.GRAY_400};

    border-bottom-right-radius: 1rem;

    :active,
    :hover {
      text-decoration: underline;
    }
  }
`;

export { Wrapper, Content };
