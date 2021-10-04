const CancelIcon = ({ color }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color ?? '#000'}
      stroke={color ?? '#000'}
      strokeWidth="3px"
      strokeLinecap="round"
      width="32px"
      height="32px"
    >
      <title id="love-emoji-icon-title">취소</title>
      <path d="M 8 8 L 24 24 M 8 24 L 24 8" />
    </svg>
  );
};

export default CancelIcon;
