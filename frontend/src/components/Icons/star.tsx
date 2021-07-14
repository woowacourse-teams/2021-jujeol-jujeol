import { COLOR } from 'src/constants';

const FILL = {
  EMPTY: 'transparent',
  HALF: 'url(#half_grad)',
  FULL: 'url(#full)',
};

type fillStatus = keyof typeof FILL;

interface Props {
  color?: string;
  status?: fillStatus;
  width?: string;
}

const StarIcon = ({ color = COLOR.WHITE_100, status = 'FULL', width = '3rem' }: Props) => {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 92.78 92.82" stroke={color} width={width}>
      <defs>
        <linearGradient id="full">
          <stop stopColor={color} />
        </linearGradient>
        <linearGradient id="half_grad">
          <stop offset="50%" stopColor={color} />
          <stop offset="50%" stopColor="transparent" stopOpacity="1" />
        </linearGradient>
      </defs>
      <polygon
        fill={FILL[status]}
        points="46.39 1.18 60.41 31.04 91.75 35.82 69.07 59.07 74.42 91.89 46.39 76.39 18.36 91.89 23.71 59.07 1.04 35.82 32.38 31.04 46.39 1.18"
      />
    </svg>
  );
};

export default StarIcon;
export type { fillStatus };
