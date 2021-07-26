import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

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

    overflow: hidden;
    display: -webkit-box;

    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
  }
`;

const Description = styled.div`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};

  span {
    margin-left: 0.3rem;
    font-size: 0.8rem;
    line-height: 1.5;
  }
`;

export { Container, Description };
