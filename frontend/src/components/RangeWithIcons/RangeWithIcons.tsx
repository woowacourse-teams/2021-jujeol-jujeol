import { ChangeEvent, InputHTMLAttributes } from 'react';
import { COLOR } from 'src/constants';
import StarIcon, { fillStatus } from '../@Icons/StarIcon';
import { Wrapper } from './RangeWithIcons.styles';

interface Props extends InputHTMLAttributes<HTMLInputElement> {
  labelText?: string;
  color?: string;
  value: number;
  setValue?: (value: number) => void;
  onTouchEnd?: () => void;
  onStart?: () => void;
  onEnd?: () => void;
  maxWidth?: string;
}

const RangeWithIcons = ({
  labelText,
  color = COLOR.WHITE,
  min = 0,
  max,
  step,
  value,
  disabled,
  maxWidth,
  setValue,
  onStart,
  onEnd,
  readOnly,
}: Props) => {
  const onChange = (event: ChangeEvent<HTMLInputElement>) => {
    setValue?.(Number(event.target.value));
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
    <Wrapper maxWidth={maxWidth}>
      <span>{labelText}</span>
      <input
        type="range"
        min={min}
        max={max}
        step={step}
        value={value}
        onChange={(event) => !disabled && onChange(event)}
        onTouchStart={onStart}
        onKeyDown={onStart}
        onMouseDown={onStart}
        onTouchEnd={onEnd}
        onMouseUp={onEnd}
        onKeyUp={onEnd}
        readOnly={readOnly}
        tabIndex={readOnly ? -1 : 0}
      />
      <div>
        {Array.from({ length: max as number }).map((_, index) => {
          return <StarIcon color={color} key={index} status={getIconsStatus(index)} />;
        })}
      </div>
    </Wrapper>
  );
};

export default RangeWithIcons;
