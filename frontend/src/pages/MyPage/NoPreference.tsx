import useDrinks from 'src/hooks/useDrinks';
import { COLOR, PATH, PREFERENCE } from 'src/constants';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import { Img } from 'src/components/@shared/Image/Image';
import { Content, Header } from './NoPreference.styles';

const NoPreference = () => {
  const {
    drinks: [drink],
  } = useDrinks();

  return (
    <>
      <Header>
        <p>선호도를 남긴 술이 없네요.</p>
        <p>선호도를 한 번 남겨보시는건 어떠세요?</p>
      </Header>

      <Content
        to={`${PATH.DRINKS}/${drink?.id}`}
        aria-label={`${drink?.name} 선호도 입력하러 가기`}
      >
        <Img src={drink?.imageResponse.small} alt="" size="X_SMALL" shape="ROUND_SQUARE" />
        <div>
          <p>{drink?.name}</p>
          <RangeWithIcons
            labelText=""
            value={0}
            color={COLOR.YELLOW_300}
            max={PREFERENCE.MAX_VALUE}
            step={PREFERENCE.STEP}
            maxWidth="200px"
            readOnly={true}
          />
        </div>
      </Content>
    </>
  );
};

export default NoPreference;
