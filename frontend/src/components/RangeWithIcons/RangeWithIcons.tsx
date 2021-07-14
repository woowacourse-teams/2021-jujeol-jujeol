import { ChangeEvent, InputHTMLAttributes } from 'react';
import { COLOR } from 'src/constants';
import StarIcon, { fillStatus } from '../Icons/star';
import { Wrapper } from './RangeWithIcons.styles';

interface Props extends InputHTMLAttributes<HTMLInputElement> {
  color: string;
  value: number;
  setValue: (value: number) => void;
}

const RangeWithIcons = ({
  color = COLOR.WHITE_100,
  min = 0,
  max,
  step,
  value,
  setValue,
}: Props) => {
  const onChange = (event: ChangeEvent<HTMLInputElement>) => {
    setValue(Number(event.target.value));
  };

  const getIconsStatus = (index: number): fillStatus => {
    if (value === 0) {
      return 'EMPTY';
    }

    if (index < value) {
      if (index === Math.floor(value)) {
        return 'HALF';
      }

      return 'FULL';
    }

    return 'EMPTY';
  };

  return (
    <Wrapper>
      <input type="range" min={min} max={max} step={step} value={value} onChange={onChange} />
      <div>
        {Array.from({ length: max as number }).map((_, index) => {
          return <StarIcon color={color} key={index} status={getIconsStatus(index)} />;
        })}
      </div>
    </Wrapper>
  );
};

export default RangeWithIcons;
