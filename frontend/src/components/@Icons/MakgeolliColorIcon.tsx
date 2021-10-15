const MakgeolliColorIcon = ({ width = '2rem', height = '2rem' }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      width={width}
      height={height}
      aria-labelledby="makgeolli-color-title"
    >
      <title id="makgeolli-color-title">색상이 있는 막걸리 잔</title>
      <path
        fill="#161616"
        d="M30.5,6H1.5a1.5,1.5,0,0,0,0,3h.63L5.94,23.64A3.23,3.23,0,0,0,9.11,26H23a3.23,3.23,0,0,0,3.17-2.39L29.87,9h.63a1.5,1.5,0,0,0,0-3ZM24.25,23.13A1.26,1.26,0,0,1,23,24H9.11a1.24,1.24,0,0,1-1.23-.86L4.19,9H27.81Z"
      />
      <path
        fill="#e0b600"
        d="M27.81,9,24.25,23.13A1.26,1.26,0,0,1,23,24H9.11a1.24,1.24,0,0,1-1.23-.86L4.19,9Z"
      />
      <path
        fill="#fff"
        d="M11.23,22.5a2,2,0,0,1-2-1.42L7,13.14a.5.5,0,0,1,.48-.64h17a.52.52,0,0,1,.4.2.5.5,0,0,1,.08.43l-2.12,7.93a2,2,0,0,1-2,1.44Z"
      />
      <path
        fill="#161616"
        d="M24.5,13l-2.12,7.93A1.52,1.52,0,0,1,20.86,22H11.23A1.53,1.53,0,0,1,9.71,21L7.51,13h17m0-1h-17a1,1,0,0,0-.8.4,1,1,0,0,0-.16.87l2.2,8A2.51,2.51,0,0,0,11.23,23h9.63a2.52,2.52,0,0,0,2.49-1.82l2.12-7.92a1,1,0,0,0-1-1.26Zm0,2h0Z"
      />
    </svg>
  );
};

export default MakgeolliColorIcon;