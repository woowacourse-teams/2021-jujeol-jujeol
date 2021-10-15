import { COLOR } from 'src/constants';

const HomeIcon = ({ width = '2rem', height = '2rem', color = COLOR.BLACK }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width={width}
      height={height}
      aria-labelledby="home-title"
    >
      <title id="home-title">집</title>
      <path d="M8.47,31a3.09,3.09,0,0,1-3.09-3.09V18.75H4.13a3.11,3.11,0,0,1-2.9-1.93,3,3,0,0,1,.7-3.33L13.8,1.89a3.16,3.16,0,0,1,4.4,0l11.87,11.6a3,3,0,0,1,.7,3.33,3.11,3.11,0,0,1-2.9,1.93H26.62v9.16A3.09,3.09,0,0,1,23.53,31Z" />
    </svg>
  );
};

export default HomeIcon;
