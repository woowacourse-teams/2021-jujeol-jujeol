import { COLOR } from 'src/constants';

const DIRECTION = {
  UP: '90',
  RIGHT: '180',
  DOWN: '270',
  LEFT: '0',
};

interface Props extends IconProps {
  direction?: keyof typeof DIRECTION;
}

const ArrowIcon = ({
  width = '32px',
  height = '32px',
  color = COLOR.BLACK,
  direction = 'LEFT',
}: Props) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      strokeWidth="3px"
      strokeLinecap="round"
      strokeLinejoin="round"
      stroke={color}
      width={width}
      height={height}
      transform={`rotate(${DIRECTION[direction]})`}
    >
      <title id="arrow-icon-title">{`${direction} 화살표`}</title>
      <path d="M 20 0 L 4 16 M 4 16 L 20 32" />
    </svg>
  );
};

export default ArrowIcon;
