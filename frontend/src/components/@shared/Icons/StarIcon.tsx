import { COLOR } from 'src/constants';

const FILL = {
  EMPTY: 'transparent',
  HALF: 'url(#half_grad)',
  FULL: 'url(#full)',
};

type fillStatus = keyof typeof FILL;

interface Props extends IconProps {
  status?: fillStatus;
}

const StarIcon = ({ color = COLOR.WHITE, status = 'FULL', width = '3rem' }: Props) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 31.58 30.52"
      stroke={color}
      width={width}
      preserveAspectRatio="xMidYMid meet"
    >
      <defs>
        <linearGradient id="full">
          <stop stopColor={color} />
        </linearGradient>
        <linearGradient id="half_grad">
          <stop offset="50%" stopColor={color} />
          <stop offset="50%" stopColor="transparent" stopOpacity="1" />
        </linearGradient>
      </defs>
      <path
        fill={FILL[status]}
        d="M22.45,29a3.52,3.52,0,0,1-1.63-.41L16,26.08a.59.59,0,0,0-.23,0,.69.69,0,0,0-.24,0l-4.8,2.53A3.46,3.46,0,0,1,9.12,29a3.52,3.52,0,0,1-3.45-4.1l.92-5.35a.51.51,0,0,0-.14-.44L2.56,15.34a3.5,3.5,0,0,1,1.94-6l5.37-.78a.5.5,0,0,0,.38-.27l2.4-4.87a3.5,3.5,0,0,1,6.28,0l2.4,4.87a.47.47,0,0,0,.37.27l5.38.78a3.5,3.5,0,0,1,1.93,6l-3.88,3.79a.51.51,0,0,0-.15.44l.92,5.35a3.46,3.46,0,0,1-.76,2.84A3.52,3.52,0,0,1,22.45,29Z"
      />
    </svg>
  );
};

export default StarIcon;
export type { fillStatus };
