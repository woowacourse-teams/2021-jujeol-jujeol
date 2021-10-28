import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Container = styled.li`
  p {
    margin: 0.5rem 0 0.2rem;

    text-align: center;
    font-size: 0.95rem;

    ${LineClamp({ lineClamp: 1 })}
  }

  a {
    width: 100%;

    ${Flex({
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
    })};
  }
`;

const Description = styled.div`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};

  span {
    margin-left: 0.3rem;
    font-size: 0.8rem;
  }
`;

export { Container, Description };
