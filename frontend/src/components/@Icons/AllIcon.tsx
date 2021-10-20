import { COLOR } from 'src/constants';

const AllIcon = ({ width = '2rem', height = '2rem', color = COLOR.BLACK }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width={width}
      height={height}
    >
      <title id="all-icon-title">All(전체)</title>
      <path d="M1.22,21.94,5.47,10h2.3L12,21.94H9.81l-.74-2.46H4.67l-.82,2.46Zm7.15-3.87L6.89,13h0l-1.53,5Z" />
      <path d="M21.26,20.21V22H13.19V10.06h2.65V20.21Z" />
      <path d="M30.78,20.21V22H22.71V10.06h2.65V20.21Z" />
    </svg>
  );
};

export default AllIcon;
