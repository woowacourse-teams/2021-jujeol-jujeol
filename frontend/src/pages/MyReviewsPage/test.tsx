import { screen, waitFor } from '@testing-library/react';

import API from 'src/apis/requests';
import { PATH } from 'src/constants';
import { validateMember } from 'src/mocks/member';
import { personalReviews } from 'src/mocks/personalReview';
import { customRender } from 'src/tests/customRenderer';
import { MockIntersectionObserver, mockMatchMedia, mockScrollTo } from 'src/tests/mockTestFunction';
import MyReviewsPage from '.';

import '@testing-library/jest-dom';

describe('로그인 된 사용자가 내가 남긴 리뷰 페이지를 이용한다.', () => {
  beforeAll(async () => {
    Object.defineProperty(global.window, 'scrollTo', { value: mockScrollTo });
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });
    Object.defineProperty(window, 'matchMedia', {
      writable: true,
      value: mockMatchMedia,
    });

    jest.spyOn(window, 'alert').mockImplementation(() => {
      return;
    });
    jest.spyOn(window, 'confirm').mockImplementation(() => {
      return true;
    });

    API.getUserInfo = jest.fn().mockReturnValue(validateMember);
    API.getPersonalReviews = jest.fn().mockReturnValue(personalReviews);

    await customRender({
      initialEntries: [PATH.MY_DRINKS],
      children: <MyReviewsPage />,
    });

    await waitFor(() => expect(API.getUserInfo).toBeCalled());
    await waitFor(() => expect(API.getPersonalReviews).toBeCalled());
  });

  it('사용자는 내가 남긴 리뷰를 확인할 수 있다.', async () => {
    expect(screen.getByRole('banner')).toHaveTextContent('내가 남긴 리뷰');

    const reviewList = screen.getByRole('list', { name: '내가 남긴 리뷰' }).querySelectorAll('li');

    personalReviews.data.every((review, index) => {
      expect(reviewList[index]).toHaveTextContent(review.drink.name);
      expect(reviewList[index]).toHaveTextContent(review.content);
      expect(reviewList[index]).toHaveTextContent(new Date(review.createdAt).toLocaleDateString());
      expect(reviewList[index].querySelector('img')).toHaveAttribute('src', review.drink.imageUrl);
      expect(reviewList[index].querySelector('img')).toHaveAccessibleName(review.drink.name);
    });
  });
});
