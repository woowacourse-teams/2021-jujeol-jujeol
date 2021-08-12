import FlexBox from '../@shared/FlexBox/FlexBox';
import Grid from '../@shared/Grid/Grid';
import Skeleton from '../@shared/Skeleton/Skeleton';
const ListItemSkeleton = ({ count }: { count: number }) => {
  return (
    <Grid rowGap="1rem">
      {Array.from({ length: count }).map((_, index) => (
        <FlexBox key={index}>
          <Skeleton type="SQUARE" size="SMALL" />
          <FlexBox flexDirection="column" margin="0 0 0 0.5rem">
            <Skeleton type="TEXT" size="MEDIUM" width="10rem" margin="0 0 0.4rem" />
            <Skeleton type="TEXT" size="SMALL" width="5rem" />
          </FlexBox>
        </FlexBox>
      ))}
    </Grid>
  );
};

export default ListItemSkeleton;
