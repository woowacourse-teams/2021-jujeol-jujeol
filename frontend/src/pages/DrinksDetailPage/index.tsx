import { MouseEventHandler, useContext, useEffect, useRef, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useHistory, useParams } from 'react-router-dom';

import UserContext from 'src/contexts/UserContext';
import API from 'src/apis/requests';
import GoBackButton from 'src/components/@shared/GoBackButton/GoBackButton';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import Review from 'src/components/Review/Review';
import Property from 'src/components/Property/Property';
import { properties } from './propertyData';
import { Section, PreferenceSection, Image, DescriptionSection, Container } from './styles';
import { COLOR, ERROR_MESSAGE, MESSAGE, PATH, PREFERENCE } from 'src/constants';
import Skeleton from 'src/components/@shared/Skeleton/Skeleton';
import DrinksDetailDescriptionSkeleton from 'src/components/Skeleton/DrinksDetailDescriptionSkeleton';

const defaultDrinkDetail = {
  name: 'name',
  englishName: 'english name',
  imageUrl: '',
  category: {
    id: 0,
    name: '',
    key: '',
  },
  alcoholByVolume: 0,
  preferenceRate: 0.0,
  preferenceAvg: 0.0,
};

const DrinksDetailPage = () => {
  const { id: drinkId } = useParams<{ id: string }>();

  const history = useHistory();

  const pageContainerRef = useRef<HTMLImageElement>(null);
  const preferenceRef = useRef<HTMLDivElement>(null);

  const [isBlinked, setIsBlinked] = useState(false);
  const [currentPreferenceRate, setCurrentPreferenceRate] = useState(
    defaultDrinkDetail.preferenceRate
  );

  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  useEffect(() => {
    pageContainerRef.current?.scrollIntoView();
  }, []);

  const { data: { data: drink = defaultDrinkDetail } = {}, isLoading } = useQuery(
    'drink-detail',
    () => API.getDrink<string>(drinkId),
    {
      retry: 0,
      onSuccess: ({ data }) => {
        setCurrentPreferenceRate(data.preferenceRate);
      },
    }
  );

  const {
    name,
    englishName,
    imageUrl,
    category: { key: categoryKey },
    alcoholByVolume,
    preferenceAvg,
  }: Drink.DetailItem = drink;

  const { mutate: updatePreference } = useMutation(
    () => {
      if (currentPreferenceRate === 0) {
        return API.deletePreference<string>(drinkId);
      }

      return API.postPreference<string, { preferenceRate: number }>(drinkId, {
        preferenceRate: currentPreferenceRate,
      });
    },
    {
      onError: (error: { code: number; message: string }) => {
        alert(ERROR_MESSAGE[error.code] ?? ERROR_MESSAGE.DEFAULT);
      },
    }
  );

  const setPreferenceRate = (value: number) => {
    setCurrentPreferenceRate(value);
  };

  const moveToLoginPage = () => {
    if (confirm(MESSAGE.LOGIN_REQUIRED_TO_UPDATE_PREFERENCE)) {
      history.push(PATH.LOGIN);
    }
  };

  const onCheckLoggedIn = () => {
    setIsBlinked(false);
    if (!isLoggedIn) {
      moveToLoginPage();
    }
  };

  const onUpdatePreference = () => {
    if (isLoggedIn) {
      updatePreference();
    }
  };

  const onMoveToPreferenceSection: MouseEventHandler<HTMLButtonElement> = () => {
    if (!isLoggedIn) {
      moveToLoginPage();
    } else {
      setIsBlinked(true);
      preferenceRef.current?.scrollIntoView({ behavior: 'smooth' });

      setTimeout(() => {
        setIsBlinked(false);
      }, 3000);
    }
  };

  return (
    <Container ref={pageContainerRef}>
      <GoBackButton color={COLOR.BLACK_900} />
      {isLoading ? (
        <Skeleton width="100" height="30rem" />
      ) : (
        <Image src={imageUrl} alt={name} loading="lazy" />
      )}
      <Section>
        <PreferenceSection ref={preferenceRef} isBlinked={isBlinked}>
          <h3>
            {currentPreferenceRate
              ? `당신의 선호도는? ${currentPreferenceRate} 점`
              : '선호도를 입력해주세요'}
          </h3>
          <RangeWithIcons
            color={COLOR.YELLOW_300}
            max={PREFERENCE.MAX_VALUE}
            step={PREFERENCE.STEP}
            value={currentPreferenceRate}
            setValue={setPreferenceRate}
            disabled={!isLoggedIn}
            onTouchStart={onCheckLoggedIn}
            onClick={onCheckLoggedIn}
            onTouchEnd={onUpdatePreference}
            onMouseUp={onUpdatePreference}
          />
          <p>다른 사람들은 평균적으로 {preferenceAvg.toFixed(1) ?? '0'}점을 줬어요</p>
        </PreferenceSection>

        <DescriptionSection>
          {isLoading && <DrinksDetailDescriptionSkeleton />}
          <h2>{name}</h2>
          <p>
            {englishName === '' ? `(${alcoholByVolume}%)` : `(${englishName}, ${alcoholByVolume}%)`}
          </p>

          <ul>
            {properties.map((property) => {
              const { Icon, content } = property.getProperty({
                categoryKey,
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

        <Review
          drinkId={drinkId}
          drinkName={name}
          preferenceRate={currentPreferenceRate}
          onMoveToPreferenceSection={onMoveToPreferenceSection}
        />
      </Section>
    </Container>
  );
};

export default DrinksDetailPage;
