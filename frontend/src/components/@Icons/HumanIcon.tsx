import { COLOR } from 'src/constants';

const HumanIcon = ({ width = '32px', height = '32px', color = COLOR.BLACK }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      width={width}
      height={height}
      fill={color}
      aria-labelledby="human-icon-title"
    >
      <title id="human-icon-title">사람</title>
      <path d="M1,31V30A15,15,0,0,1,9.71,16.38a9,9,0,1,1,12.57,0A15,15,0,0,1,31,30v1Z" />
    </svg>
  );
};

export default HumanIcon;
