import '@testing-library/jest-dom';
import { screen, waitFor, fireEvent } from '@testing-library/react';
import { customRender } from 'src/tests/customRenderer';
import { MockIntersectionObserver, mockScrollTo } from 'src/tests/mockTestFunction';
import { validateMember } from 'src/mocks/member';
import { drinksDetail } from 'src/mocks/drinksDetail';
import { drinksReviews } from 'src/mocks/drinksReviews';
import API from 'src/apis/requests';

import { PATH } from 'src/constants';

import DrinksDetailPage from '.';

describe('로그인 된 사용자가 상세페이지를 이용한다.', () => {
  beforeAll(async () => {
    Object.defineProperty(global.window, 'scrollTo', { value: mockScrollTo });
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });

    jest.spyOn(window, 'alert').mockImplementation(() => {
      return;
    });
    jest.spyOn(window, 'confirm').mockImplementation(() => {
      return true;
    });

    API.getUserInfo = jest.fn().mockReturnValue(validateMember);
    API.getDrink = jest.fn().mockReturnValue(drinksDetail);
    API.getReview = jest.fn().mockReturnValue(drinksReviews);
    API.postReview = jest.fn().mockReturnValue(true);
    API.editReview = jest.fn().mockReturnValue(true);
    API.deleteReview = jest.fn().mockReturnValue(true);
  });

  beforeEach(async () => {
    customRender({ initialEntries: [`${PATH.DRINKS}/0`], children: <DrinksDetailPage /> });

    await waitFor(() => expect(API.getUserInfo).toBeCalled());
    await waitFor(() => expect(API.getDrink).toBeCalled());
    await waitFor(() => expect(API.getReview).toBeCalled());
  });

  it('사용자는 상세페이지에서 주류 정보를 확인할 수 있다.', async () => {
    expect(screen.getByAltText(drinksDetail.data.name).getAttribute('src')).toBe(
      drinksDetail.data.imageUrl
    );
    expect(screen.getByText(drinksDetail.data.name)).toBeVisible();
    expect(
      screen.getByText(`(${drinksDetail.data.englishName}, ${drinksDetail.data.alcoholByVolume}%)`)
    ).toBeVisible();
    expect(
      screen.getByText(`당신의 선호도는? ${drinksDetail.data.preferenceRate} 점`)
    ).toBeVisible();
    expect(
      screen.getByText(`다른 사람들은 평균적으로 ${drinksDetail.data.preferenceAvg}점을 줬어요`)
    ).toBeVisible();
  });

  it('로그인 된 사용자는 상세페이지에서 선호도를 남길 수 있다.', async () => {
    const preferenceRate = 4.5;
    const preferenceInput = screen.getByRole('slider');

    fireEvent.click(preferenceInput);
    expect(window.confirm).not.toBeCalled();

    fireEvent.change(preferenceInput, { target: { value: preferenceRate } });
    expect(screen.getByText(`당신의 선호도는? ${preferenceRate} 점`)).toBeVisible();
  });

  it('로그인 된 사용자는 상세페이지에서 선호도를 삭제할 수 있다.', async () => {
    const preferenceRate = 0;
    const preferenceInput = await screen.findByRole('slider');

    fireEvent.click(preferenceInput);
    expect(window.confirm).not.toBeCalled();

    fireEvent.change(preferenceInput, { target: { value: preferenceRate } });
    expect(screen.getByText(`선호도를 입력해주세요`)).toBeVisible();
  });

  it('사용자는 리뷰를 조회할 수 있다.', async () => {
    expect(screen.getByText(`리뷰 ${drinksReviews.pageInfo.totalSize}개`)).toBeVisible();

    drinksReviews.data.every((review) => {
      expect(screen.getByText(review.content)).toBeVisible();
    });
  });

  it('로그인 된 사용자는 상세페이지에서 리뷰를 남길 수 있다.', async () => {
    const review = 'good12312341234';
    API.getReview = jest.fn().mockReturnValue({
      data: [
        {
          id: 4,
          author: {
            id: 3,
            name: 'perenok',
          },
          content: review,
          createdAt: new Date(),
          modifiedAt: null,
        },
        ...drinksReviews.data,
      ],
      pageInfo: drinksReviews.pageInfo,
    });

    const reviewInput = screen.getByRole('textbox');
    const submitButton = screen.getByRole('button', { name: '작성 완료' });

    fireEvent.change(reviewInput, { target: { value: review } });
    fireEvent.click(submitButton);

    await waitFor(() => expect(API.postReview).toBeCalled());
    await waitFor(() => expect(API.getReview).toBeCalled());

    expect(reviewInput).toHaveDisplayValue('');
    expect(screen.getByText(review)).toBeVisible();
  });

  it('로그인 된 사용자는 하루에 두 번이상 리뷰를 작성할 수 없다.', async () => {
    API.postReview = jest.fn().mockImplementation(() => {
      throw new Error();
    });

    const review = 'good12312341234';

    const reviewInput = screen.getByRole('textbox');
    const submitButton = screen.getByRole('button', { name: '작성 완료' });

    fireEvent.change(reviewInput, { target: { value: review } });
    fireEvent.click(submitButton);

    await waitFor(() => expect(API.postReview).toBeCalled());
    expect(window.alert).toBeCalled();
  });

  it('로그인 된 사용자는 상세페이지에서 리뷰를 수정할 수 있다.', async () => {
    const review = 'good12312341234';
    const reviewInput = screen.getByRole('textbox');
    const submitButton = screen.getByRole('button', { name: '작성 완료' });

    fireEvent.change(reviewInput, { target: { value: review } });
    fireEvent.click(submitButton);

    await waitFor(() => expect(API.postReview).toBeCalled());

    const editButton = screen.getByLabelText('내 리뷰 글 수정하기 버튼');

    fireEvent.click(editButton);

    const editTextBox = screen.getByPlaceholderText('리뷰를 작성해 주세요');
    const editedReview = "oh, that's so delicious";
    const editSubmitButton = screen.getByRole('button', { name: '수정하기' });

    API.getReview = jest.fn().mockReturnValue({
      data: [
        {
          id: 4,
          author: {
            id: 3,
            name: 'perenok',
          },
          content: editedReview,
          createdAt: new Date(),
          modifiedAt: null,
        },
        ...drinksReviews.data,
      ],
      pageInfo: drinksReviews.pageInfo,
    });

    fireEvent.change(editTextBox, { target: { value: editedReview } });
    fireEvent.click(editSubmitButton);

    await waitFor(() => expect(API.editReview).toBeCalled());
    await waitFor(() => expect(API.getReview).toBeCalled());

    expect(screen.getByText(editedReview)).toBeVisible();
  });

  it('로그인 된 사용자는 상세페이지에서 리뷰를 삭제할 수 있다.', async () => {
    const review = 'good12312341234';
    const reviewInput = screen.getByRole('textbox');
    const submitButton = screen.getByRole('button', { name: '작성 완료' });

    fireEvent.change(reviewInput, { target: { value: review } });
    fireEvent.click(submitButton);

    await waitFor(() => expect(API.postReview).toBeCalled());

    const editButton = screen.getByLabelText('내 리뷰 글 수정하기 버튼');

    fireEvent.click(editButton);

    const deleteButton = screen.getByRole('button', { name: '삭제하기' });

    fireEvent.click(deleteButton);

    expect(window.confirm).toBeCalled();

    await waitFor(() => expect(API.deleteReview).toBeCalled());
    await waitFor(() => expect(API.getReview).toBeCalled());

    drinksReviews.data.every((review) => {
      expect(screen.getByText(review.content)).toBeVisible();
    });
  });
});
