import '@testing-library/jest-dom';
import { screen, render, waitFor, fireEvent, getByLabelText } from '@testing-library/react';
import { MemoryRouter as Router } from 'react-router-dom';
import { LocationDescriptor } from 'history';
import APIProvider from 'src/apis/APIProvider';
import API from 'src/apis/requests';
import { drinksDetail } from 'src/mocks/drinksDetail';
import drinksReviews from 'src/mocks/drinksReviews';
import DrinksDetailPage from '.';
import { MockIntersectionObserver, mockScrollTo } from 'src/mocks/test';
import { UserProvider } from 'src/contexts/UserContext';
import ModalProvider from 'src/components/Modal/ModalProvider';

interface Props {
  initialEntries: LocationDescriptor[];
  children: React.ReactNode;
}

const customRender = ({ initialEntries, children }: Props) => {
  render(
    <APIProvider>
      <UserProvider>
        <ModalProvider>
          <Router initialEntries={initialEntries}>{children}</Router>
        </ModalProvider>
      </UserProvider>
    </APIProvider>
  );
};

describe('로그인 된 사용자가 상세페이지를 이용한다.', () => {
  const modalPortalRoot = document.createElement('div');
  modalPortalRoot.setAttribute('id', 'modal');
  modalPortalRoot.setAttribute('role', 'dialog');
  document.body.appendChild(modalPortalRoot);

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

    API.getUserInfo = jest.fn().mockReturnValue({ data: { id: 0, nickname: '123', bio: '1234' } });
    API.getDrink = jest.fn().mockReturnValue({ data: drinksDetail });
    API.getReview = jest
      .fn()
      .mockReturnValue({ data: drinksReviews.data, pageInfo: drinksReviews.pageInfo });
    API.postReview = jest.fn().mockReturnValue(true);
    API.editReview = jest.fn().mockReturnValue(true);
  });

  beforeEach(async () => {
    customRender({ initialEntries: [`/drinks/0`], children: <DrinksDetailPage /> });

    await waitFor(() => expect(API.getUserInfo).toBeCalled());
    await waitFor(() => expect(API.getDrink).toBeCalled());
    await waitFor(() => expect(API.getReview).toBeCalled());
  });

  it('사용자는 상세페이지에서 주류 정보를 확인할 수 있다.', async () => {
    expect(screen.getByAltText(drinksDetail.name).getAttribute('src')).toBe(drinksDetail.imageUrl);
    expect(screen.getByText(drinksDetail.name)).toBeVisible();
    expect(
      screen.getByText(`(${drinksDetail.englishName}, ${drinksDetail.alcoholByVolume}%)`)
    ).toBeVisible();
    expect(screen.getByText(`당신의 선호도는? ${drinksDetail.preferenceRate} 점`)).toBeVisible();
    expect(
      screen.getByText(`다른 사람들은 평균적으로 ${drinksDetail.preferenceAvg}점을 줬어요`)
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

    // TODO: user API 변경에 따른 테스트 코드 추가
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

    await waitFor(() => expect(API.postReview).toBeCalledTimes(1));
    await waitFor(() => expect(API.getReview).toBeCalledTimes(1));

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

    await waitFor(() => expect(API.postReview).toBeCalledTimes(1));
    expect(window.alert).toBeCalled();
  });

  it('로그인 된 사용자는 상세페이지에서 리뷰를 수정할 수 있다.', async () => {
    // 글 작성
    const review = 'good12312341234';
    const reviewInput = screen.getByRole('textbox');
    const submitButton = screen.getByRole('button', { name: '작성 완료' });

    fireEvent.change(reviewInput, { target: { value: review } });
    fireEvent.click(submitButton);

    await waitFor(() => expect(API.postReview).toBeCalledTimes(1));

    // 작성완료
    const editButton = screen.getByLabelText('내 리뷰 글 수정하기 버튼');

    fireEvent.click(editButton);

    // 수정모달
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

    await waitFor(() => expect(API.editReview).toBeCalledTimes(1));
    await waitFor(() => expect(API.getReview).toBeCalledTimes(1));

    expect(screen.getByText(editedReview)).toBeVisible();
  });
});

describe('로그인 되지 않은 사용자가 상세페이지를 이용한다.', () => {
  beforeEach(async () => {
    Object.defineProperty(global.window, 'scrollTo', { value: mockScrollTo });
    Object.defineProperty(global.window, 'IntersectionObserver', {
      value: MockIntersectionObserver,
    });

    jest.spyOn(window, 'confirm').mockImplementation(() => {
      return true;
    });

    API.getUserInfo = jest.fn().mockImplementation(() => {
      throw new Error();
    });
    API.getDrink = jest.fn().mockReturnValue({ data: drinksDetail });
    API.getReview = jest
      .fn()
      .mockReturnValue({ data: drinksReviews.data, pageInfo: drinksReviews.pageInfo });

    customRender({ initialEntries: [`/drinks/0`], children: <DrinksDetailPage /> });

    await waitFor(() => expect(API.getUserInfo).toBeCalledTimes(1));
    await waitFor(() => expect(API.getDrink).toBeCalledTimes(1));
    await waitFor(() => expect(API.getReview).toBeCalledTimes(1));
  });

  it('로그인 되지 않은 사용자가 상세페이지에서 선호도를 남기려고 할 때 로그인하라는 창이 뜬다.', async () => {
    const preferenceInput = await screen.findByRole('slider');

    fireEvent.click(preferenceInput);
    expect(window.confirm).toBeCalled();
  });

  it('로그인 되지 않은 사용자가 상세페이지에서 리뷰를 남기려고 할 때 로그인하라는 창이 뜬다.', async () => {
    const reviewInput = screen.getByRole('textbox');

    fireEvent.click(reviewInput);
    expect(window.confirm).toBeCalled();
  });
});
