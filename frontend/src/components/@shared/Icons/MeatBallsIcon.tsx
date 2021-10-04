const MeatBallsIcon = ({ color }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 100 100"
      fill={color ?? '#000'}
      width="32px"
      height="32px"
    >
      <circle cx="30" cy="50" r="6" />
      <circle cx="50" cy="50" r="6" />
      <circle cx="70" cy="50" r="6" />
    </svg>
  );
};

export default MeatBallsIcon;
