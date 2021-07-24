import { useContext, useEffect, useRef, useState } from 'react';
import { useInfiniteQuery, useMutation, useQuery } from 'react-query';
import { useHistory, useParams } from 'react-router-dom';
import API from 'src/apis/requests';
import Property from 'src/components/Property/Property';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import Review from 'src/components/Review/Review';
import { COLOR, ERROR_MESSAGE, MESSAGE, PATH, PREFERENCE } from 'src/constants';
import { properties, categoryIdType } from './propertyData';
import {
  Section,
  PreferenceSection,
  Image,
  DescriptionSection,
  InfinityScrollPoll,
} from './styles';
import UserContext from 'src/contexts/UserContext';

const defaultDrinkDetail = {
  name: 'name',
  englishName: 'english name',
  imageUrl: '',
  category: {
    id: 0,
    name: '',
  },
  alcoholByVolume: 0,
  preferenceRate: 0.0,
  preferenceAvg: 0.0,
};

const DrinksDetailPage = () => {
  const { id: drinkId } = useParams<{ id: string }>();

  const scrollAreaRef = useRef<HTMLDivElement>(null);
  const infinityPollRef = useRef<HTMLDivElement>(null);
  const [drinkInfo, setDrinkInfo] = useState(defaultDrinkDetail);

  const history = useHistory();
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  const {
    name,
    englishName,
    imageUrl,
    category: { id: categoryId, name: categoryName },
    alcoholByVolume,
    preferenceRate,
    preferenceAvg,
  } = drinkInfo;

  const DrinkQuery = useQuery('drink-detail', () => API.getDrink<string>(drinkId), {
    retry: 0,
    onSuccess: ({ data }) => {
      setDrinkInfo(data);
    },
  });

  const { data: reviewData, fetchNextPage } = useInfiniteQuery<{
    data: Review.ReviewItem[];
    pageInfo: { currentPage: number; lastPage: number; countPerPage: number; totalSize: number };
  }>('reviews', ({ pageParam = 1 }) => API.getReview<string>({ id: drinkId, page: pageParam }), {
    getNextPageParam: ({ pageInfo }) => {
      return pageInfo.currentPage < pageInfo.lastPage ? pageInfo.currentPage + 1 : undefined;
    },
  });
  const reviews = reviewData?.pages?.map((page) => page.data).flat() ?? [];
  const [
    {
      pageInfo: { totalSize: totalReviewSize },
    },
  ] = reviewData?.pages ?? [{ pageInfo: { totalSize: 0 } }];

  const { mutate: updatePreference } = useMutation(
    () => {
      if (preferenceRate === 0) {
        return API.deletePreference<string>(drinkId);
      }

      return API.postPreference<
        string,
        {
          preferenceRate: number;
        }
      >(drinkId, { preferenceRate });
    },
    {
      onError: (error: { code: number; message: string }) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  const setPreferenceRate = (value: number) => {
    setDrinkInfo((prev) => ({ ...prev, preferenceRate: value }));
  };

  const onCheckLoggedIn = () => {
    if (!isLoggedIn) {
      if (confirm(MESSAGE.LOGIN_REQUIRED_TO_UPDATE_PREFERENCE)) {
        history.push(PATH.LOGIN);
      }
    }
  };

  const onUpdatePreference = () => {
    if (isLoggedIn) {
      updatePreference();
    }
  };

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          fetchNextPage();
        }
      });
    },
    { root: scrollAreaRef.current }
  );

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    if (infinityPollRef.current) {
      observer.observe(infinityPollRef.current);
    }
  }, [infinityPollRef.current]);

  return (
    <>
      <Image src={imageUrl} alt={name} />
      <Section ref={scrollAreaRef}>
        <PreferenceSection>
          <h3>
            {preferenceRate ? `당신의 선호도는? ${preferenceRate} 점` : '선호도를 입력해주세요'}
          </h3>
          <RangeWithIcons
            color={COLOR.YELLOW_300}
            max={PREFERENCE.MAX_VALUE}
            step={PREFERENCE.STEP}
            value={preferenceRate}
            setValue={setPreferenceRate}
            disabled={!isLoggedIn}
            onTouchStart={onCheckLoggedIn}
            onClick={onCheckLoggedIn}
            onTouchEnd={onUpdatePreference}
          />
          <p>
            다른 사람들은 평균적으로 <span>{preferenceAvg ?? '0'}</span>점을 줬어요
          </p>
        </PreferenceSection>

        <DescriptionSection>
          <h2>{name}</h2>
          <p>
            {englishName === '' ? `(${alcoholByVolume}%)` : `(${englishName}, ${alcoholByVolume}%)`}
          </p>

          <ul>
            {properties.map((property) => {
              const { Icon, content } = property.getProperty({
                categoryId: categoryId as categoryIdType,
                alcoholByVolume,
              });

              return (
                <li key={property.id}>
                  <Property Icon={Icon} title={property.name} content={content} />
                </li>
              );
            })}
          </ul>
        </DescriptionSection>

        <Review reviews={reviews} totalSize={totalReviewSize} />

        <InfinityScrollPoll ref={infinityPollRef} />
      </Section>
    </>
  );
};

export default DrinksDetailPage;
