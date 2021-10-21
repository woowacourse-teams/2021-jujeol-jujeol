import { COLOR } from 'src/constants';

const MeatBallsIcon = ({ width = '2rem', height = '2rem', color = COLOR.BLACK }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 100 100"
      fill={color}
      width={width}
      height={height}
    >
      <circle cx="30" cy="50" r="6" />
      <circle cx="50" cy="50" r="6" />
      <circle cx="70" cy="50" r="6" />
    </svg>
  );
};

export default MeatBallsIcon;
