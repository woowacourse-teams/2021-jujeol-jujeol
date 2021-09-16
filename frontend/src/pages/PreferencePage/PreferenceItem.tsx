import React, { useState } from 'react';
import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import { Img } from 'src/components/@shared/Image/Image';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import { COLOR, PREFERENCE } from 'src/constants';
import { DrinkDescription, Title } from './styles';

const PreferenceItem = ({
  id,
  initialValue,
  imageUrl,
  name,
  onUpdatePreference,
}: {
  id: number;
  initialValue: number;
  imageUrl: string;
  name: string;
  onUpdatePreference: (preferenceRate: number) => void;
}) => {
  const [preferenceRate, setPreferenceRate] = useState(initialValue);

  return (
    <FlexBox alignItems="center" justifyContent="center">
      <Img src={imageUrl} alt={name} shape="ROUND_SQUARE" size="X_SMALL" />
      <DrinkDescription>
        <Title>{name}</Title>
        <RangeWithIcons
          color={COLOR.YELLOW_300}
          max={PREFERENCE.MAX_VALUE}
          step={PREFERENCE.STEP}
          value={preferenceRate}
          setValue={setPreferenceRate}
          width="100%"
          onMouseUp={() => onUpdatePreference(preferenceRate)}
          onTouchEnd={() => onUpdatePreference(preferenceRate)}
        />
      </DrinkDescription>
    </FlexBox>
  );
};

const MemoizedPreferenceItem = React.memo(PreferenceItem);

export default MemoizedPreferenceItem;
