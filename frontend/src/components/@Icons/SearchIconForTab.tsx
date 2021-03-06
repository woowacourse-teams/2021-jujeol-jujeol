import { COLOR } from 'src/constants';

const SearchIconForTab = ({ width = '32px', height = '32px', color = COLOR.BLACK }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      width={width}
      height={height}
      fill={color}
      aria-labelledby="search-icon-for-tab-title"
    >
      <title id="search-icon-for-tab-title">검색</title>
      <path d="M27.07,31A2.9,2.9,0,0,1,25,30.14l-7.33-7.33a11.51,11.51,0,1,1,5.13-5.13L30.14,25a2.91,2.91,0,0,1,0,4.13l-1,1A2.86,2.86,0,0,1,27.07,31ZM12.51,7.38a5.14,5.14,0,0,0,0,10.27,4.9,4.9,0,0,0,1.72-.3,5.09,5.09,0,0,0,3.12-3.11,5,5,0,0,0,.3-1.73A5.14,5.14,0,0,0,12.51,7.38Z" />
    </svg>
  );
};

export default SearchIconForTab;
