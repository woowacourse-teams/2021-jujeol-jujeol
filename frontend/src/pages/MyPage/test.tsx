import '@testing-library/jest-dom';
import { Redirect, Route, Switch } from 'react-router-dom';
import { Location } from 'history';
import { screen, waitFor, fireEvent } from '@testing-library/react';
import { customRender } from 'src/tests/customRenderer';

import MyPage from '.';
import API from 'src/apis/requests';
import { MockIntersectionObserver, mockScrollTo } from 'src/mocks/mockTestFunction';
import { noPersonalReviews, personalReviews } from 'src/mocks/personalReview';
import { noPersonalDrinks, personalDrinks } from 'src/mocks/personalDrinks';
import drinks from 'src/mocks/drinks';

let testLocation: Location<unknown>;

const renderMypage = async () => {
  await customRender({
    initialEntries: [`/mypage`],
    children: (
      <>
        <Switch>
          <Route exact path="/mypage">
            <MyPage />
          </Route>
          <Route exact path="/mypage/drinks">
            선호도 남긴 술
          </Route>
          <Route exact path="/mypage/reviews">
            내가 남긴 리뷰
          </Route>
          <Redirect to="/mypage" />
        </Switch>
        <Route
          path="*"
          render={({ location }) => {
            testLocation = location;
            return null;
          }}
        />
      </>
    ),
  });

  await waitFor(() => expect(API.getUserInfo).toBeCalled());
  await waitFor(() => expect(API.getPersonalDrinks).toBeCalled());
  await waitFor(() => expect(API.getPersonalReviews).toBeCalled());
};

const userInfo = { data: { id: 0, nickname: '청바지_123', bio: '청춘은 바로 지금' } };

describe('로그인 된 사용자가 마이페이지를 이용한다.', () => {
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

    API.getUserInfo = jest.fn().mockReturnValue(userInfo);
    API.getDrinks = jest.fn().mockReturnValue({
      data: drinks,
      pageInfo: { currentPage: 1, lastPage: 1, countPerPage: 7, totalSize: 7 },
    });
  });

  it('사용자는 마이페이지에서 닉네임, bio, 선호도를 남긴 술, 내가 남긴 리뷰를 확인할 수 있다.', async () => {
    API.getPersonalDrinks = jest.fn().mockReturnValue(personalDrinks);
    API.getPersonalReviews = jest.fn().mockReturnValue(personalReviews);
    await renderMypage();

    const numOfMyDrinks = screen.getAllByText('선호도를 남긴 술')[0].closest('li');
    const numOfMyReviews = screen.getAllByText('내가 남긴 리뷰')[0].closest('li');

    const MyDrinksList = screen
      .getByRole('heading', { name: /선호도를 남긴 술/i })
      .closest('section')
      ?.querySelector('ul');
    const MyReviewsList = screen
      .getByRole('heading', {
        name: /내가 남긴 리뷰/i,
      })
      .closest('section')
      ?.querySelector('ul');

    expect(numOfMyDrinks).toHaveTextContent(personalDrinks.pageInfo.totalSize + '개');
    expect(numOfMyReviews).toHaveTextContent(personalReviews.pageInfo.totalSize + '개');
    personalDrinks.data.every((drink, index) => {
      const item = MyDrinksList?.querySelectorAll('li')[index];

      expect(item).toHaveTextContent(drink.name);
      expect(item).toHaveTextContent(drink.preferenceRate.toFixed(1));
      expect(item?.querySelector('img')).toHaveAccessibleName(drink.name);
    });
    personalReviews.data.every((review, index) => {
      const item = MyReviewsList?.querySelectorAll('li')[index];

      expect(item).toHaveTextContent(review.drink.name);
      expect(item).toHaveTextContent(new Date(review.createdAt).toLocaleDateString());
      expect(item).toHaveTextContent(review.content);
      expect(item?.querySelector('img')).toHaveAccessibleName(review.drink.name);
    });
  });

  it('선호도를 남긴 술이 없는 경우, 선호도를 남길것을 안내한다.', async () => {
    API.getPersonalDrinks = jest.fn().mockReturnValue(noPersonalDrinks);
    API.getPersonalReviews = jest.fn().mockReturnValue(noPersonalReviews);
    await renderMypage();

    const MyDrinksSection = screen
      .getByRole('heading', { name: /선호도를 남긴 술/i })
      .closest('section');

    expect(MyDrinksSection).toHaveTextContent('선호도를 남긴 술이 없네요.');
    expect(MyDrinksSection).toHaveTextContent('선호도를 한 번 남겨보시는건 어떠세요?');
    expect(MyDrinksSection).toHaveTextContent(drinks[0].name);
    expect(MyDrinksSection?.querySelector('img')).toHaveAccessibleName(drinks[0].name);
  });

  it('선호도를 남긴 술이 없고, 리뷰가 없는 경우, 리뷰를 남길 것을 안내한다.', async () => {
    API.getPersonalDrinks = jest.fn().mockReturnValue(noPersonalDrinks);
    API.getPersonalReviews = jest.fn().mockReturnValue(noPersonalReviews);
    await renderMypage();

    const MyReviewsSection = screen
      .getByRole('heading', {
        name: /내가 남긴 리뷰/i,
      })
      .closest('section');

    expect(MyReviewsSection).toHaveTextContent('남긴 리뷰가 없습니다.');
    expect(MyReviewsSection).toHaveTextContent('리뷰를 남겨보시는건 어떠세요?');
  });

  it('선호도를 남긴 술이 있고, 리뷰가 없는 경우, 선호도를 남긴 술 기반으로(최근 3개) 리뷰 작성을 권한다.', async () => {
    API.getPersonalDrinks = jest.fn().mockReturnValue(personalDrinks);
    API.getPersonalReviews = jest.fn().mockReturnValue(noPersonalReviews);
    await renderMypage();

    const MyReviewsListItems =
      screen
        .getByRole('heading', {
          name: /내가 남긴 리뷰/i,
        })
        .closest('section')
        ?.querySelectorAll('li') ?? [];

    personalDrinks.data
      .slice(0, personalDrinks.data.length > 3 ? 3 : personalDrinks.data.length)
      .every((drink, index) => {
        expect(MyReviewsListItems[index]).toHaveTextContent(drink.name);
        expect(MyReviewsListItems[index]).toHaveTextContent(drink.preferenceRate.toFixed(1));
        expect(MyReviewsListItems[index].querySelector('img')).toHaveAccessibleName(drink.name);
        expect(MyReviewsListItems[index]).toHaveTextContent('리뷰하기');
      });
  });

  it('사용자는 선호도를 남긴 술 더보기를 이용해, 선호도를 남긴 술 전체 페이지로 이동할 수 있다.', async () => {
    API.getPersonalDrinks = jest.fn().mockReturnValue(personalDrinks);
    API.getPersonalReviews = jest.fn().mockReturnValue(personalReviews);
    await renderMypage();

    const drinkShowMoreButton = screen
      .getByRole('heading', { name: /선호도를 남긴 술/i })
      .closest('section')
      ?.querySelector('button') as HTMLButtonElement;

    fireEvent.click(drinkShowMoreButton);

    expect(testLocation.pathname).toBe('/mypage/drinks');
  });

  it('사용자는 내가 남긴 리뷰 더보기를 이용해, 내가 남긴 리뷰 술 전체 페이지로 이동할 수 있다.', async () => {
    API.getPersonalDrinks = jest.fn().mockReturnValue(personalDrinks);
    API.getPersonalReviews = jest.fn().mockReturnValue(personalReviews);
    await renderMypage();

    const reviewShowMoreButton = screen
      .getByRole('heading', { name: /내가 남긴 리뷰/i })
      .closest('section')
      ?.querySelector('button') as HTMLButtonElement;

    fireEvent.click(reviewShowMoreButton);

    expect(testLocation.pathname).toBe('/mypage/reviews');
  });
});
