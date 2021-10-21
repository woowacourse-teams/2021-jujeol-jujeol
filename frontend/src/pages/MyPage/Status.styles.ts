import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';
import { COLOR } from 'src/constants';

const StatusList = styled.ul`
  width: 100%;
  height: 5.5rem;
  padding: 0 1rem;

  ${Flex({ justifyContent: 'center', alignItems: 'center' })};
  position: relative;

  ::after {
    content: '';

    height: 3rem;
    position: absolute;

    border-left: 1px solid ${COLOR.PURPLE_600};
  }
`;

const StatusItem = styled.li`
  width: 100%;
  height: 4rem;

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

  > p {
    font-size: 1.1rem;
    font-weight: 700;
  }

  > p:last-child {
    margin-top: 0.5rem;

    font-size: 0.8rem;
    font-weight: 300;
  }
`;

export { StatusList, StatusItem };
