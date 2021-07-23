import { css } from '@emotion/react';

const Flex = ({ flexDirection, justifyContent, flexWrap, alignItems }: React.CSSProperties) => css`
  display: flex;
  flex-direction: ${flexDirection ?? 'row'};
  justify-content: ${justifyContent ?? 'flex-start'};
  flex-wrap: ${flexWrap ?? 'nowrap'};
  align-items: ${alignItems ?? 'stretch'};
`;

export default Flex;
