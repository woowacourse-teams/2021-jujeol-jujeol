import { useContext, useEffect, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useHistory, useParams } from 'react-router-dom';
import UserContext from 'src/contexts/UserContext';
import API from 'src/apis/requests';
import Property from 'src/components/Property/Property';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import Review from 'src/components/Review/Review';
import { COLOR, ERROR_MESSAGE, MESSAGE, PATH, PREFERENCE } from 'src/constants';
import { properties } from './propertyData';
import { Section, PreferenceSection, Image, DescriptionSection } from './styles';

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

  const [drinkInfo, setDrinkInfo] = useState(defaultDrinkDetail);

  const history = useHistory();
  const isLoggedIn = useContext(UserContext)?.isLoggedIn;

  const {
    name,
    englishName,
    imageUrl,
    category: { key: categoryKey },
    alcoholByVolume,
    preferenceRate,
    preferenceAvg,
  } = drinkInfo;

  const DrinkDetailQuery = useQuery('drink-detail', () => API.getDrink<string>(drinkId), {
    retry: 0,
    onSuccess: ({ data }) => {
      setDrinkInfo(data);
    },
  });

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

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <>
      <Image src={imageUrl} alt={name} />
      <Section>
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
            onMouseUp={onUpdatePreference}
          />
          <p>다른 사람들은 평균적으로 {preferenceAvg ?? '0'}점을 줬어요</p>
        </PreferenceSection>

        <DescriptionSection>
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

        <Review drinkId={drinkId} />
      </Section>
    </>
  );
};

export default DrinksDetailPage;
