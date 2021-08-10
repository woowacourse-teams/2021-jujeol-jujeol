import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const Header = styled.div`
  text-align: center;
  margin-bottom: 1rem;
  line-height: 1.25;
`;

const Content = styled.div`
  ${Flex({ columnGap: '0.5rem', justifyContent: 'center' })}

  p {
    margin-bottom: 0.5rem;
  }
`;

export { Header, Content };
