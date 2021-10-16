import { COLOR } from 'src/constants';

const MakgeolliIcon = ({ width = '2rem', height = '2rem', color = COLOR.WHITE }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width={width}
      height={height}
      aria-labelledby="makgeolli-icon-title"
    >
      <title id="makgeolli-icon-title">막걸리 잔</title>
      <path d="M30.5,6H1.5a1.5,1.5,0,0,0,0,3h.63L5.94,23.64A3.23,3.23,0,0,0,9.11,26H23a3.23,3.23,0,0,0,3.17-2.39L29.87,9h.63a1.5,1.5,0,0,0,0-3ZM24.25,23.13A1.26,1.26,0,0,1,23,24H9.11a1.24,1.24,0,0,1-1.23-.86L4.19,9H27.81Z" />
      <path d="M24.5,13l-2.12,7.93A1.52,1.52,0,0,1,20.86,22H11.23A1.53,1.53,0,0,1,9.71,21L7.51,13Z" />
    </svg>
  );
};

export default MakgeolliIcon;
