import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Description = styled.div`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};

  span {
    margin-left: 0.3rem;
    font-size: 0.8rem;
    line-height: 1.5;
  }
`;

export { Description };
