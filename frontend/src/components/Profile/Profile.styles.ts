import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.section`
  ${Flex({ alignItems: 'center' })}

  width: 100%;
  height: 7rem;
  padding: 2rem;

  background-color: ${COLOR.PURPLE_500};

  div {
    ${Flex({ flexDirection: 'column' })}
    width: 100%;
    height: 100%;

    margin-left: 1rem;

    h3 {
      font-size: 1.1rem;
      font-weight: 700;
      margin-bottom: 0.5rem;
    }

    p {
      font-size: 0.8rem;
      line-height: 1.25;
      font-weight: 400;
    }
  }

  svg {
    width: 72px;
    height: 72px;
  }
`;

export { Container };
