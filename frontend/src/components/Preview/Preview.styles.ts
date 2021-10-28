import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const PreviewSection = styled.section`
  width: 100%;
  min-height: 14rem;
  padding: 1.3rem;
  margin: 0.5rem 0;

  ${Flex({ flexDirection: 'column' })}
`;

const Header = styled.div`
  margin-bottom: 1rem;

  ${Flex({ justifyContent: 'space-between', alignItems: 'center' })};
`;

const MoveViewAllPageButton = styled.button<{ fontSize: string }>`
  padding: 0;

  ${Flex({ justifyContent: 'center', alignItems: 'center' })}
  background-color: transparent;

  span {
    margin-right: 0.1rem;

    ${({ fontSize }) => fontSize && `font-size:  ${fontSize}`};

    color: ${COLOR.GRAY_100};
  }
`;

export { Header, MoveViewAllPageButton, PreviewSection };
