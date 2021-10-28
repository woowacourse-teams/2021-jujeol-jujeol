import { COLOR } from 'src/constants';

interface Props extends IconProps {
  direction?: 'UP' | 'RIGHT' | 'DOWN' | 'LEFT';
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
    >
      <title id="arrow-icon-title">{`${direction} 화살표`}</title>
      {direction === 'LEFT' && <path d="M 20 0 L 4 16 M 4 16 L 20 32" />}
      {direction === 'RIGHT' && <path d="M 4 0 L 20 16 M 20 16 L 4 32" />}
      {direction === 'UP' && <path d="M 0 24 L 16 8 M 16 8 L 32 24" />}
      {direction === 'DOWN' && <path d="M 0 8 L 16 24 M 16 24 L 32 8" />}
    </svg>
  );
};

export default ArrowIcon;
