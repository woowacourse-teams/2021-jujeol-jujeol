import FlexBox from 'src/components/FlexBox/FlexBox';
import Skeleton from './Skeleton';

const ListItemSkeleton = ({ count }: { count: number }) => {
  return (
    <FlexBox flexDirection="column" rowGap="1rem">
      {Array.from({ length: count }).map((_, index) => (
        <FlexBox key={index} columnGap="1rem">
          <Skeleton type="SQUARE" size="SMALL" />
          <FlexBox flexDirection="column" rowGap="0.5rem">
            <Skeleton type="TEXT" size="MEDIUM" width="10rem" />
            <Skeleton type="TEXT" size="SMALL" width="5rem" />
          </FlexBox>
        </FlexBox>
      ))}
    </FlexBox>
  );
};

export default ListItemSkeleton;
