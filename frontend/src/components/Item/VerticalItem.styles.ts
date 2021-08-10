import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Container = styled.li`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

  :last-child {
    position: relative;
    &::after {
      content: '';
      position: absolute;
      right: -1.5rem;
      width: 1.5rem;
      height: 100%;
    }
  }

  p {
    margin-top: 0.5rem;

    font-size: 0.95rem;
    line-height: 1.5;

    ${LineClamp({ lineClamp: 1 })}
  }
`;

export { Container };
