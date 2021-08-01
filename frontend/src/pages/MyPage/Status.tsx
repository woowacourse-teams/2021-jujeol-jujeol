import { StatusItem, StatusList } from './Status.styles';

interface Props {
  myDrinksCount: number;
  myReviewsCount: number;
}

const stateConfig = ({ myDrinksCount, myReviewsCount }: Props) => [
  {
    count: myDrinksCount,
    title: '내가 마신 술',
  },
  {
    count: myReviewsCount,
    title: '내가 남긴 리뷰',
  },
];

const Status = ({ myDrinksCount, myReviewsCount }: Props) => {
  return (
    <StatusList>
      {stateConfig({ myDrinksCount, myReviewsCount }).map((state) => (
        <StatusItem key={state.title}>
          <p>{state.count}개</p>
          <p>{state.title}</p>
        </StatusItem>
      ))}
    </StatusList>
  );
};

export default Status;
