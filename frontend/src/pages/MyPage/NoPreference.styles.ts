import { Link } from 'react-router-dom';
import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';

const Header = styled.div`
  text-align: center;
  margin-bottom: 1rem;

  flex-grow: 1;
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}
`;

const Content = styled(Link)`
  ${Flex({ alignItems: 'center', justifyContent: 'center' })}

  img {
    margin-right: 0.5rem;
  }

  p {
    margin-bottom: 0.2rem;
  }
`;

export { Content, Header };
