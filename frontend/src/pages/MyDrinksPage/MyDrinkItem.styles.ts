import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Description = styled.div`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};

  span {
    font-size: 0.8rem;
    margin-left: 0.3rem;
  }
`;

export { Description };
