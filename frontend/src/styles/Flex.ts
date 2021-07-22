import { css } from '@emotion/react';

interface FlexContainer {
  flexDirection?: 'row' | 'row-reverse' | 'column' | 'column-reverse';
  justifyContent?: 'flex-start' | 'flex-end' | 'center' | 'space-between' | 'space-around';
  flexWrap?: 'nowrap' | 'wrap' | 'wrap-reverse';
  alignItems?: 'stretch' | 'flex-start' | 'flex-end' | 'center' | 'baseline';
}

const Flex = ({ flexDirection, justifyContent, flexWrap, alignItems }: FlexContainer) => css`
  display: flex;
  flex-direction: ${flexDirection ?? 'row'};
  justify-content: ${justifyContent ?? 'flex-start'};
  flex-wrap: ${flexWrap ?? 'nowrap'};
  align-items: ${alignItems ?? 'stretch'};
`;

export default Flex;
