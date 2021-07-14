import { useState } from 'react';
import HumanIcon from 'src/components/Icons/human';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import { COLOR, PREFERENCE } from 'src/constants';
import { drinksDetail } from 'src/mock/drinksDetail';
import { Section, PreferenceSection } from './styles';

const DrinksDetailPage = () => {
  // url params 받아서 get request
  const [drinkInfo, setDrinkInfo] = useState(drinksDetail);
  const {
    id: detailId,
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
      <img src={imageUrl} alt={name} width="100%" />
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

        <section style={{ marginBottom: '1.5rem' }}>
          <div style={{ marginBottom: '2rem' }}>
            <h2 style={{ fontSize: '1.5rem', fontWeight: 'bold', marginBottom: '0.5rem' }}>
              {name}
            </h2>
            <p>{`(${englishName}, ${alcoholByVolume}%)`}</p>
          </div>

          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                marginRight: '2rem',
              }}
            >
              <HumanIcon color="white" />
              <div
                style={{
                  marginLeft: '0.6rem',
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                  justifyContent: 'center',
                }}
              >
                <p>주종</p>
                <p>{categoryName}</p>
              </div>
            </div>

            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
              }}
            >
              <HumanIcon color="white" />
              <div
                style={{
                  marginLeft: '0.6rem',
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                  justifyContent: 'center',
                }}
              >
                <p>도수</p>
                <p>{alcoholByVolume}</p>
              </div>
            </div>
          </div>
        </section>
      </Section>
    </>
  );
};

export default DrinksDetailPage;
