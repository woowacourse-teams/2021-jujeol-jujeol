import React, { useState } from 'react';
import FlexBox from 'src/components/@shared/FlexBox/FlexBox';
import { Img } from 'src/components/@shared/Image/Image';
import RangeWithIcons from 'src/components/RangeWithIcons/RangeWithIcons';
import { COLOR, PREFERENCE } from 'src/constants';

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
  onUpdatePreference: any;
}) => {
  const [preferenceRate, setPreferenceRate] = useState(initialValue);

  return (
    <FlexBox>
      <Img src={imageUrl} alt={name} shape="ROUND_SQUARE" size="X_SMALL" />
      <div style={{ marginLeft: '1rem' }}>
        <p style={{ marginBottom: '0.5rem' }}>{name}</p>
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
      </div>
    </FlexBox>
  );
};

const MemoizedPreferenceItem = React.memo(PreferenceItem);

export default MemoizedPreferenceItem;
