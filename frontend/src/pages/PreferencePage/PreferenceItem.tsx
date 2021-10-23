import React, { useState } from 'react';

import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import { Img } from 'src/components/@shared/Image/Image';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import { COLOR, PREFERENCE } from 'src/constants';
import { DrinkDescription, Title } from './styles';

const PreferenceItem = ({
  initialValue,
  labelText,
  imageUrl,
  name,
  onUpdatePreference,
  onClickImage,
}: {
  initialValue: number;
  labelText: string;
  imageUrl: string;
  name: string;
  onUpdatePreference: (preferenceRate: number) => void;
  onClickImage: () => void;
}) => {
  const [preferenceRate, setPreferenceRate] = useState(initialValue);

  return (
    <FlexBox alignItems="center" justifyContent="center">
      <Img src={imageUrl} alt="" shape="ROUND_SQUARE" size="X_SMALL" onClick={onClickImage} />
      <DrinkDescription>
        <Title>{name}</Title>
        <RangeWithIcons
          color={COLOR.YELLOW_300}
          labelText={labelText}
          max={PREFERENCE.MAX_VALUE}
          step={PREFERENCE.STEP}
          value={preferenceRate}
          setValue={setPreferenceRate}
          width="100%"
          onEnd={() => onUpdatePreference(preferenceRate)}
        />
      </DrinkDescription>
    </FlexBox>
  );
};

const MemoizedPreferenceItem = React.memo(PreferenceItem);

export default MemoizedPreferenceItem;
