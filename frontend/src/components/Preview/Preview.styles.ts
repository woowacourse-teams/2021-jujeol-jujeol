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
    font-size: 1.1rem;
    font-weight: 700;
    margin-bottom: 1rem;
  }
`;

const MoveViewAllPageButton = styled.button<{ fontSize: string }>`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })}

  padding: 0;

  background-color: transparent;
  border: 0;

  span {
    ${({ fontSize }) => fontSize && `font-size:  ${fontSize}`};
    line-height: 1.5;
    color: ${COLOR.WHITE_200};
  }
`;

export { PreviewSection, Header, MoveViewAllPageButton };
