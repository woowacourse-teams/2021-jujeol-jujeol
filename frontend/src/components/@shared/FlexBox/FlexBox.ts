import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';

const FlexBox = styled.div<React.CSSProperties>`
  ${({ flexDirection, justifyContent, flexWrap, alignItems, rowGap, columnGap }) =>
    Flex({ flexDirection, justifyContent, flexWrap, alignItems, rowGap, columnGap })}
  ${({ margin }) => `margin: ${margin}`};
`;

export default FlexBox;
