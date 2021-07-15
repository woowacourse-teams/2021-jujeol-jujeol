import { useState } from 'react';
import Property from 'src/components/Property/Property';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import Review from 'src/components/Review/Review';
import { COLOR, PREFERENCE } from 'src/constants';
import { drinksDetail } from 'src/mock/drinksDetail';
import { properties, categoryIdType } from './propertyData';
import { Section, PreferenceSection, Image, DescriptionSection } from './styles';

const DrinksDetailPage = () => {
  const [drinkInfo, setDrinkInfo] = useState(drinksDetail);
  const {
    name,
    englishName,
    imageUrl,
    category: { id: categoryId },
    alcoholByVolume,
    preferenceRate,
  } = drinkInfo;

  const setPreferenceRate = (value: number) => {
    setDrinkInfo((prev) => ({ ...prev, preferenceRate: value }));
  };

  return (
    <>
      <Image src={imageUrl} alt={name} />
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
          <p>{`(${englishName}, ${alcoholByVolume}%)`}</p>

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

        <Review />
      </Section>
    </>
  );
};

export default DrinksDetailPage;
