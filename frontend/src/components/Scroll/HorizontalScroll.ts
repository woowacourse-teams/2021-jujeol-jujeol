import styled from '@emotion/styled';

const HorizontalScroll = styled.div<{ margin: string; padding: string }>`
  ${({ margin }) => margin && `margin: ${margin}`};
  ${({ padding }) => padding && `padding: ${padding}`};

  overflow-x: auto;
  scroll-behavior: smooth;
  -ms-overflow-style: none;
  scrollbar-width: none;
  ::-webkit-scrollbar {
    display: none;
  }
`;

export { HorizontalScroll };
