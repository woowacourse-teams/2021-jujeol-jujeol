import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import Skeleton from '../@shared/Skeleton/Skeleton';

const PersonalReviewItemSkeleton = ({ count }: { count: number }) => {
  return (
    <>
      {Array.from({ length: count }).map((_, index) => (
        <Card key={index} padding="1rem" backgroundColor={COLOR.GRAY_100}>
          <FlexBox flexDirection="row" columnGap="0.5rem">
            <Skeleton type="SQUARE" size="X_SMALL" />
            <FlexBox flexDirection="column" rowGap="0.2rem">
              <Skeleton type="TEXT" size="SMALL" width="10rem" />
              <Skeleton type="TEXT" size="LARGE" width="12rem" />
            </FlexBox>
          </FlexBox>
        </Card>
      ))}
    </>
  );
};

export default PersonalReviewItemSkeleton;
