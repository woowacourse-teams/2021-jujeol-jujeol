import Image from 'src/components/@shared/Image/Image';
import PersonalReviewCard from 'src/components/Card/PersonalReviewCard';
import StarIcon from 'src/components/Icons/star';
import Preview from 'src/components/Preview/Preview';
import Profile from 'src/components/Profile/Profile';
import Horizontal from 'src/components/ScrollList/Horizontal';

import { Header, Statistics, VerticalItem, VerticalScrollList } from './styles';

const MyPage = () => {
  return (
    <>
      <Header>
        <button />
        <h2>내정보</h2>
      </Header>

      <Profile src="http://placehold.it/72x72" nickname="청바지_122" bio="청춘은 바로 지금" />

      <Statistics>
        <li>
          <p>43개</p>
          <p>내가 마신 술</p>
        </li>

        <li>
          <p>15개</p>
          <p>내가 남긴 리뷰</p>
        </li>
      </Statistics>

      <Preview title="내가 마신 술">
        <Horizontal count={7}>
          {Array.from({ length: 7 }).map((_, index) => (
            <VerticalItem key={index}>
              <Image
                src="http://placehold.it/120x120"
                alt="호감도를 입력한 술"
                shape="ROUND_SQUARE"
                size="LARGE"
              />
              <p>KGB 라임</p>
              <div>
                <StarIcon width="0.8rem" color="yellow" />
                <span>3.5</span>
              </div>
            </VerticalItem>
          ))}
        </Horizontal>
      </Preview>

      <Preview title="내가 남긴 리뷰">
        <VerticalScrollList>
          {Array.from({ length: 3 }).map((_, index) => (
            <li key={index}>
              <PersonalReviewCard />
            </li>
          ))}
        </VerticalScrollList>
      </Preview>
    </>
  );
};

export default MyPage;
