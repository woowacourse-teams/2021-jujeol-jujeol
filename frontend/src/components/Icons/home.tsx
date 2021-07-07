import React from 'react';

interface Props {
  color: string;
}

const HomeIcon = ({ color }: Props) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 114.63 105.28"
      fill={color}
      width="32px"
      height="32px"
    >
      <path d="M106.64,65.28H96.82V94.34a10.94,10.94,0,0,1-10.94,10.94H29.76A10.94,10.94,0,0,1,18.82,94.34V65.28H8A8,8,0,0,1,2.36,51.66L51.68,2.33A8,8,0,0,1,63,2.33l49.32,49.33A8,8,0,0,1,106.64,65.28Z" />
    </svg>
  );
};

export default HomeIcon;
