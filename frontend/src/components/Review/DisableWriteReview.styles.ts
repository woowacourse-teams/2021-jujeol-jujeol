import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.div`
  div {
    ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};
    margin-bottom: 2rem;

    p {
      color: ${COLOR.WHITE_200};
      word-break: keep-all;
      font-size: 0.9rem;
      line-height: 1.5;
    }

    button {
      ${Flex({ justifyContent: 'center', alignItems: 'center' })}
      position: relative;

      width: 70%;
      height: 2.5rem;
      margin: 0.5rem auto;

      font-weight: 700;
      background-color: ${COLOR.YELLOW_300};
      border: none;
      border-radius: 2rem;

      :active {
        background-color: ${COLOR.YELLOW_300}90;
      }

      div {
        border-color: ${COLOR.BLACK_900};
        margin: 0;
        margin-left: 0.5rem;
      }
    }
  }
`;

export { Container };
