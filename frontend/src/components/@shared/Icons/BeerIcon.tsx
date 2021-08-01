const BeerIcon = ({ color }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width="32px"
      height="32px"
      aria-labelledby="beer-title"
    >
      <title id="beer-title">맥주 잔</title>
      <path d="M9.58,25.38a1,1,0,0,1-.94-.94V15.06a.95.95,0,0,1,1.89,0v9.38A1,1,0,0,1,9.58,25.38Z" />
      <path d="M13.35,25.38a.94.94,0,0,1-.94-.94V15.06a.94.94,0,0,1,1.88,0v9.38A.94.94,0,0,1,13.35,25.38Z" />
      <path d="M17.11,25.38a.94.94,0,0,1-.94-.94V15.06a.94.94,0,0,1,1.88,0v9.38A.94.94,0,0,1,17.11,25.38Z" />
      <path d="M26.18,11.31H22.75V9.79a4.08,4.08,0,0,0,.94-2.72,4.21,4.21,0,0,0-4.23-4.19,4.14,4.14,0,0,0-2.65.93h-.26a5.19,5.19,0,0,0-9.22,0A4.26,4.26,0,0,0,3,8a4.2,4.2,0,0,0,.94,2.65V28.41A2.57,2.57,0,0,0,6.48,31H20.21a2.56,2.56,0,0,0,2.54-2.59v-.94h3.43A2.81,2.81,0,0,0,29,24.66V14.12A2.82,2.82,0,0,0,26.18,11.31Zm-5.31,17.1a.68.68,0,0,1-.66.71H6.48a.68.68,0,0,1-.66-.71V12a4.09,4.09,0,0,0,1.41.24,4.2,4.2,0,0,0,3-1.23,5.23,5.23,0,0,0,3.45,0,4.23,4.23,0,0,0,5.64.29h.14a4,4,0,0,0,1.43-.22Zm-1.39-19a1.88,1.88,0,0,1-.34,0,.93.93,0,0,0-.84.26,2.31,2.31,0,0,1-1.66.7,2.34,2.34,0,0,1-1.93-1,.94.94,0,0,0-.78-.42,1.06,1.06,0,0,0-.45.11,3.31,3.31,0,0,1-3.09,0,.94.94,0,0,0-1.23.31A2.35,2.35,0,1,1,7.72,5.74a.92.92,0,0,0,1.09-.62,3.3,3.3,0,0,1,6.25,0,.92.92,0,0,0,1.09.62,2.23,2.23,0,0,1,.81,0,.91.91,0,0,0,.84-.27,2.3,2.3,0,0,1,1.66-.69,2.36,2.36,0,0,1,1.66.68,2.46,2.46,0,0,1,.69,1.68,2.27,2.27,0,0,1-2.33,2.33Zm7.64,15.22a.94.94,0,0,1-.94.94H22.75V13.19h3.43a.94.94,0,0,1,.94.93Z" />
    </svg>
  );
};

export default BeerIcon;
