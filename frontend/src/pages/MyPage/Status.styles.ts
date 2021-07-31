import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';
import { COLOR } from 'src/constants';

const StatusList = styled.ul`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};

  width: 100%;
  height: 5.5rem;

  position: relative;

  background-color: ${COLOR.PURPLE_500};

  ::after {
    content: '';
    height: 3rem;
    border-left: 1px solid ${COLOR.PURPLE_900};

    position: absolute;
  }
`;

const StatusItem = styled.li`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

  width: 100%;
  height: 4rem;

  > p {
    font-size: 1.1rem;
    font-weight: 700;
  }

  > p:last-child {
    font-size: 0.8rem;
    font-weight: 300;
    margin-top: 0.5rem;
  }
`;

export { StatusList, StatusItem };
