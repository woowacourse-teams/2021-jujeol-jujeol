import FlexBox from 'src/components/FlexBox/FlexBox';
import Skeleton from './Skeleton';

const PersonalDrinkItemSkeleton = ({
  count,
  size,
}: {
  count: number;
  size: 'X_SMALL' | 'SMALL' | 'MEDIUM' | 'LARGE' | 'X_LARGE';
}) => {
  return (
    <>
      {Array.from({ length: count }).map((_, index) => (
        <FlexBox key={index} flexDirection="column" rowGap="0.5rem" alignItems="center">
          <Skeleton type="SQUARE" size={size} />
          <Skeleton type="TEXT" size="SMALL" width="5rem" />
        </FlexBox>
      ))}
    </>
  );
};

export default PersonalDrinkItemSkeleton;
