import Grid from '../@shared/Grid/Grid';

interface Props {
  count?: number;
  rowGap?: string;
  children: React.ReactNode;
}

const List = ({ count, rowGap = '1rem', children }: Props) => {
  return (
    <Grid row={count} rowGap={rowGap} padding="0 2rem">
      {children}
    </Grid>
  );
};

export default List;
