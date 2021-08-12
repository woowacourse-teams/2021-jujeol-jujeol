import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import Skeleton from '../@shared/Skeleton/Skeleton';

const DrinksDetailDescriptionSkeleton = () => {
  return (
    <FlexBox flexDirection="column" alignItems="center" rowGap="1rem">
      <Skeleton type="TEXT" size="LARGE" width="100%" />
      <Skeleton type="TEXT" size="MEDIUM" width="80%" />
      <FlexBox columnGap="1rem">
        <Skeleton type="SQUARE" size="X_SMALL" width="6rem" />
        <Skeleton type="SQUARE" size="X_SMALL" width="6rem" />
      </FlexBox>
    </FlexBox>
  );
};

export default DrinksDetailDescriptionSkeleton;
