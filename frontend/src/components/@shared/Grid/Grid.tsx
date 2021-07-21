import { Container, GridStyle } from './Grid.styles';

interface Props extends GridStyle {
  children: React.ReactNode;
}

const Grid = ({
  col,
  row,
  colGap,
  rowGap,
  colMin,
  colMax,
  rowMin,
  rowMax,
  padding,
  children,
}: Props) => {
  return (
    <Container
      col={col}
      row={row}
      colGap={colGap}
      rowGap={rowGap}
      colMin={colMin}
      colMax={colMax}
      rowMin={rowMin}
      rowMax={rowMax}
      padding={padding}
    >
      {children}
    </Container>
  );
};

export default Grid;
