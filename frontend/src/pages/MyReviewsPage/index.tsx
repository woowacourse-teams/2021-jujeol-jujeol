import { useEffect, useRef } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useHistory } from 'react-router-dom';
import API from 'src/apis/requests';
import Arrow from 'src/components/@shared/Arrow/Arrow';
import Grid from 'src/components/@shared/Grid/Grid';
import InfinityScrollPoll from 'src/components/@shared/InfinityScrollPoll/InfinityScrollPoll';
import PersonalReviewItemSkeleton from 'src/components/Skeleton/PersonalReviewItemSkeleton';
import PersonalReviewItem from 'src/components/Item/PersonalReviewItem';
import useInfinityScroll from 'src/hooks/useInfinityScroll';
import { Container, Header } from './styles';
import NavigationHeader from 'src/components/Header/NavigationHeader';

const MyReviewsPage = () => {
  const history = useHistory();

  const {
    data: { pages } = {},
    fetchNextPage,
    hasNextPage,
    isFetching,
  } = useInfiniteQuery(
    'personal-review',
    ({ pageParam = 1 }) => API.getPersonalReviews({ page: pageParam, size: 10 }),
    {
      retry: 0,
      getNextPageParam: ({ pageInfo }) => {
        const { currentPage, lastPage } = pageInfo;

        return currentPage < lastPage ? currentPage + 1 : undefined;
      },
    }
  );

  const observerTargetRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
  useInfinityScroll({ target: observerTargetRef, fetchNextPage, hasNextPage });

  const personalReviews = pages?.map((page) => page.data).flat();

  return (
    <>
      <NavigationHeader title="내가 남긴 리뷰" />

      <Container>
        <ul title="내가 남긴 리뷰">
          {personalReviews?.map((review) => (
            <PersonalReviewItem key={review.id} review={review} />
          ))}
          <InfinityScrollPoll ref={observerTargetRef} />
        </ul>
        {isFetching && (
          <Grid row={7} rowGap="1rem">
            <PersonalReviewItemSkeleton count={7} />
          </Grid>
        )}
      </Container>
    </>
  );
};

export default MyReviewsPage;
