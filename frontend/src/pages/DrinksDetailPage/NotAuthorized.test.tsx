import '@testing-library/jest-dom';
import { screen, render, waitFor, fireEvent } from '@testing-library/react';
import { MemoryRouter as Router } from 'react-router-dom';
import { LocationDescriptor } from 'history';
import APIProvider from 'src/apis/APIProvider';
import API from 'src/apis/requests';
import { drinksDetail } from 'src/mocks/drinksDetail';
import drinksReviews from 'src/mocks/drinksReviews';
import DrinksDetailPage from '.';
import { MockIntersectionObserver, mockScrollTo } from 'src/mocks/mockTestFunction';
import { UserProvider } from 'src/contexts/UserContext';

interface Props {
  initialEntries: LocationDescriptor[];
  children: React.ReactNode;
}

const customRender = ({ initialEntries, children }: Props) => {
  render(
    <APIProvider>
      <UserProvider>
        <Router initialEntries={initialEntries}>{children}</Router>
      </UserProvider>
    </APIProvider>
  );
};

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

    await waitFor(() => expect(API.getUserInfo).toBeCalled());
    await waitFor(() => expect(API.getDrink).toBeCalled());
    await waitFor(() => expect(API.getReview).toBeCalled());
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
