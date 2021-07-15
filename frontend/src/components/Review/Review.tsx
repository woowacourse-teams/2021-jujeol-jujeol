import { useState } from 'react';
import { COLOR } from 'src/constants';
import drinksReviews from 'src/mock/drinksReviews';
import Card from '../Card/Card';
import ReviewCard from '../Card/ReviewCard';
import { Wrapper, Form, ReviewList } from './Review.styles';

const Review = () => {
  const [content, setContent] = useState('');

  return (
    <Wrapper>
      <h2>리뷰 {drinksReviews.data.length}개</h2>
      <Card padding="1rem" backgroundColor={COLOR.WHITE_200}>
        <Form>
          <div>
            <span>청바지_1234</span>
            <span>{`${content.length}/300`}</span>
          </div>
          <textarea
            placeholder="리뷰를 등록해 주세요. 사용자 별 리뷰는 하루에 1개만 등록할 수 있습니다."
            value={content}
            onChange={({ target }) => setContent(target.value)}
          />
          <button>작성 완료</button>
        </Form>
      </Card>

      <ReviewList>
        {drinksReviews.data.map((review) => {
          return (
            <li key={review.id}>
              <ReviewCard review={review} />
            </li>
          );
        })}
      </ReviewList>
    </Wrapper>
  );
};

export default Review;
