import styled from '@emotion/styled';

const HorizontalList = styled.ul<{ count: number }>`
  display: grid;
  grid-template-columns: ${({ count }) => `repeat(${count}, 1fr)`};
  grid-column-gap: 1rem;

  white-space: nowrap;
  overflow-x: auto;
  -ms-overflow-style: none;
  scrollbar-width: none;
  ::-webkit-scrollbar {
    display: none;
  }
`;

export { HorizontalList };
