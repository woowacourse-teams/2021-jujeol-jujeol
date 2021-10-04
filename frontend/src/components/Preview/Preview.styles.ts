import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';
import { COLOR } from 'src/constants';

const PreviewSection = styled.section`
  width: 100%;
  min-height: 14rem;
  padding: 1.3rem;
  margin: 0.5rem 0;

  ${Flex({ flexDirection: 'column' })}
`;

const Header = styled.div`
  ${Flex({ justifyContent: 'space-between', alignItems: 'flex-start' })};

  h3 {
    margin-bottom: 1rem;

    font-size: 1.1rem;
    font-weight: 700;
  }
`;

const MoveViewAllPageButton = styled.button<{ fontSize: string }>`
  padding: 0;

  ${Flex({ justifyContent: 'center', alignItems: 'center' })}
  background-color: transparent;
  border: 0;

  span {
    marign-right: 0.1rem;

    ${({ fontSize }) => fontSize && `font-size:  ${fontSize}`};
    line-height: 1.5;
    color: ${COLOR.GRAY_100};
  }
`;

export { PreviewSection, Header, MoveViewAllPageButton };
