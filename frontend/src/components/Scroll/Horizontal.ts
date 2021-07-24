import styled from '@emotion/styled';

const Horizontal = styled.div<{ marginLeft: string; paddingLeft: string }>`
  ${({ marginLeft }) => marginLeft && `margin-left: ${marginLeft}`};
  ${({ paddingLeft }) => paddingLeft && `padding-left: ${paddingLeft}`};

  overflow-x: auto;
  scroll-behavior: smooth;
  -ms-overflow-style: none;
  scrollbar-width: none;
  ::-webkit-scrollbar {
    display: none;
  }
`;

export { Horizontal };
