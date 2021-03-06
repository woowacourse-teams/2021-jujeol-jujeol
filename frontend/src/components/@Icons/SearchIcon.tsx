import { COLOR } from 'src/constants';

const SearchIcon = ({ width = '32px', height = '32px', color = COLOR.WHITE }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 283.46 283.46"
      fill={color}
      width={width}
      height={height}
      aria-labelledby="search-icon-title"
    >
      <title id="search-icon-title">검색</title>
      <polygon className="st0" points="443.58,285.8 445.82,285.8 445.82,285.8 443.58,285.8 " />
      <path
        className="st1"
        d="M277.24,254.12l-79.2-79.2c12.34-17.48,19.6-38.8,19.6-61.83c0-59.26-48.04-107.3-107.3-107.3
	S3.02,53.83,3.02,113.09c0,59.26,48.04,107.3,107.3,107.3c24.7,0,47.45-8.35,65.58-22.38l78.71,78.71c6.24,6.24,16.37,6.24,22.61,0
	l0,0C283.48,270.49,283.48,260.37,277.24,254.12z M110.33,192.15c-43.66,0-79.06-35.4-79.06-79.06c0-43.66,35.4-79.06,79.06-79.06
	c43.66,0,79.06,35.4,79.06,79.06C189.38,156.76,153.99,192.15,110.33,192.15z"
      />
    </svg>
  );
};

export default SearchIcon;
