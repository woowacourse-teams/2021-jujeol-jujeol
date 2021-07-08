interface Props {
  color: string;
}

const HumanIcon = ({ color }: Props) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 120.6 106"
      fill={color}
      width="32px"
      height="32px"
    >
      <path d="M120.6,106H0A60.62,60.62,0,0,1,33.25,56.89a38.48,38.48,0,0,0,54.1,0A60.62,60.62,0,0,1,120.6,106Z" />
      <path d="M89.8,29.5a29.22,29.22,0,0,1-4.48,15.59,28.76,28.76,0,0,1-3.12,4.16,29.5,29.5,0,0,1-43.8,0,28.76,28.76,0,0,1-3.12-4.16A29.22,29.22,0,0,1,30.8,29.5a29.5,29.5,0,0,1,59,0Z" />
    </svg>
  );
};

export default HumanIcon;
