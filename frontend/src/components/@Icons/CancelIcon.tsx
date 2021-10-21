import { COLOR } from 'src/constants';

const CancelIcon = ({ width = '2rem', height = '2rem', color = COLOR.BLACK }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      stroke={color}
      strokeWidth="3px"
      strokeLinecap="round"
      width={width}
      height={height}
    >
      <title id="love-emoji-icon-title">취소</title>
      <path d="M 8 8 L 24 24 M 8 24 L 24 8" />
    </svg>
  );
};

export default CancelIcon;
