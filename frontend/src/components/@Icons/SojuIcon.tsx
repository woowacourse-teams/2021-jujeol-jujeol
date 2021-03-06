import { COLOR } from 'src/constants';

const SojuIcon = ({ width = '32px', height = '32px', color = COLOR.WHITE }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width={width}
      height={height}
      aria-labelledby="soju-icon-title"
    >
      <title id="soju-icon-title">소주 잔</title>
      <path d="M23.78,7.17a2.23,2.23,0,0,0-1.68-.76H9.9a2.23,2.23,0,0,0-1.69.77,2.27,2.27,0,0,0-.53,1.76l2,14.71a2.24,2.24,0,0,0,2.2,1.94h8.33a2.23,2.23,0,0,0,2.21-1.95L24.32,8.92A2.2,2.2,0,0,0,23.78,7.17ZM21,23.45a.74.74,0,0,1-.73.65H11.9a.75.75,0,0,1-.73-.64l-2-14.71a.74.74,0,0,1,.18-.6A.72.72,0,0,1,9.9,7.9H22.1a.73.73,0,0,1,.56.25.7.7,0,0,1,.18.59Z" />
      <path d="M13.09,22.51H19a.51.51,0,0,0,.51-.45l1.32-9.6a.19.19,0,0,0-.19-.21h-9.3a.19.19,0,0,0-.19.22l1.42,9.6A.51.51,0,0,0,13.09,22.51Z" />
    </svg>
  );
};

export default SojuIcon;
