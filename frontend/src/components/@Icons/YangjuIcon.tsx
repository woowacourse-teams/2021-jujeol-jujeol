import { COLOR } from 'src/constants';

const YangjuIcon = ({ width = '32px', height = '32px', color = COLOR.WHITE }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width={width}
      height={height}
      aria-labelledby="yangju-icon-title"
    >
      <title id="yangju-icon-title">양주 잔</title>
      <path d="M24.08,28.5H7.91a3,3,0,0,1-3-2.87l-.83-19a3,3,0,0,1,3-3.13H24.91a3,3,0,0,1,3,3.13l-.83,19A3,3,0,0,1,24.08,28.5Zm-17-23a1,1,0,0,0-.72.31,1,1,0,0,0-.28.73l.83,19a1,1,0,0,0,1,1H24.08a1,1,0,0,0,1-1l.83-19a1,1,0,0,0-.28-.73,1,1,0,0,0-.72-.31Z" />
      <path d="M23.05,15.5h-.27l.4-1a2.83,2.83,0,0,0-1.55-3.69L14.89,8.09a2.82,2.82,0,0,0-3.68,1.54L8.8,15.51a1,1,0,0,0-.83,1l.34,8a1,1,0,0,0,1,.94H22.7a1,1,0,0,0,1-.94l.34-8A1,1,0,0,0,23.05,15.5ZM11,15.5l2.09-5.11a.82.82,0,0,1,1.08-.45l6.74,2.76a.84.84,0,0,1,.45.44.88.88,0,0,1,0,.64l-.71,1.72Z" />
    </svg>
  );
};

export default YangjuIcon;
