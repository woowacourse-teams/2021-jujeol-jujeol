import styled from '@emotion/styled';

interface GridStyle extends Omit<React.CSSProperties, 'translate'> {
  col?: number;
  row?: number;
  colGap?: string;
  rowGap?: string;
  colMin?: string;
  colMax?: string;
  rowMin?: string;
  rowMax?: string;
}

const Grid = styled.ul<GridStyle>`
  display: grid;
  ${({ col }) => col && `grid-template-columns: repeat(${col}, 1fr);`}
  ${({ colGap }) => colGap && `grid-column-gap: ${colGap};`}
  ${({ colMin, colMax }) =>
    (colMin || colMax) &&
    `grid-template-columns: minmax(${colMin ?? 'auto'}, ${colMax ?? 'auto'});`}
  ${({ row }) => row && `grid-template-rows: repeat(${row}, 1fr);`}
  ${({ rowGap }) => rowGap && `grid-row-gap: ${rowGap};`}
  ${({ rowMin, rowMax }) =>
    (rowMin || rowMax) && `grid-template-rows: minmax(${rowMin ?? 'auto'}, ${rowMax ?? 'auto'});`}
    
  ${({ padding }) => padding && `padding: ${padding};`}
  ${({ justifyItems }) => justifyItems && `justify-items: ${justifyItems}`}
`;

export default Grid;
