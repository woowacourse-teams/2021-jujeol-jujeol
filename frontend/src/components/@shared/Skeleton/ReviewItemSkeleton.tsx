import FlexBox from 'src/components/FlexBox/FlexBox';
import { COLOR } from 'src/constants';
import Card from '../Card/Card';
import Skeleton from './Skeleton';

const ReviewItemSkeleton = ({ count }: { count: number }) => {
  return (
    <>
      {Array.from({ length: count }).map((_, index) => (
        <Card key={index} padding="1rem" backgroundColor={COLOR.WHITE_200}>
          <FlexBox flexDirection="column" rowGap="0.5rem">
            <FlexBox columnGap="0.5rem">
              <Skeleton type="CIRCLE" size="X_SMALL" />
              <FlexBox flexDirection="column" rowGap="0.5rem">
                <Skeleton type="TEXT" size="SMALL" width="5rem" />
                <Skeleton type="TEXT" size="X_SMALL" width="4rem" />
              </FlexBox>
            </FlexBox>
            <Skeleton type="TEXT" size="LARGE" />
          </FlexBox>
        </Card>
      ))}
    </>
  );
};

export default ReviewItemSkeleton;
