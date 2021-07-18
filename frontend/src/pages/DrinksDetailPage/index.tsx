import { useState } from 'react';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';
import API from 'src/apis/requests';
import Property from 'src/components/Property/Property';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import Review from 'src/components/Review/Review';
import { COLOR, PREFERENCE } from 'src/constants';
import { properties, categoryIdType } from './propertyData';
import { Section, PreferenceSection, Image, DescriptionSection } from './styles';
import notFoundImage from 'src/assets/default.png';
import { onImageError } from 'src/utils/error';

const defaultDrinkDetail = {
  name: 'name',
  englishName: 'english name',
  imageUrl: notFoundImage,
  category: {
    id: 0,
    name: '',
  },
  alcoholByVolume: 0,
  preferenceRate: 0.0,
};

const DrinksDetailPage = () => {
  const { id: drinkId } = useParams<{ id: string }>();
  const [drinkInfo, setDrinkInfo] = useState(defaultDrinkDetail);
  const [reviews, setReviews] = useState([]);
  const reviewSearchParams = new URLSearchParams('?page=1');

  const DrinkQuery = useQuery('drink-detail', () => API.getDrink<string>(drinkId), {
    retry: 0,
    onSuccess: ({ data }) => {
      setDrinkInfo(data);
    },
  });

  const ReviewsQuery = useQuery(
    'reviews',
    () => API.getReview<string>(drinkId, reviewSearchParams),
    {
      retry: 0,
      onSuccess: ({ data, pageInfo }) => {
        setReviews(data);
      },
    }
  );

  const {
    name,
    englishName,
    imageUrl,
    category: { id: categoryId, name: categoryName },
    alcoholByVolume,
    preferenceRate,
  } = drinkInfo;

  const setPreferenceRate = (value: number) => {
    setDrinkInfo((prev) => ({ ...prev, preferenceRate: value }));
  };

  return (
    <>
      <Image src={imageUrl ?? notFoundImage} alt={name} onError={onImageError} />
      <Section>
        <PreferenceSection>
          <h3>당신의 선호도는? {preferenceRate} 점</h3>
          <RangeWithIcons
            color={COLOR.YELLOW_300}
            max={PREFERENCE.MAX_VALUE}
            step={PREFERENCE.STEP}
            value={preferenceRate}
            setValue={setPreferenceRate}
          />
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

        <Review reviews={reviews} />
      </Section>
    </>
  );
};

export default DrinksDetailPage;
