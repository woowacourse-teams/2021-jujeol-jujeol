import { COLOR } from 'src/constants';

const LoginIcon = ({ width = '2rem', height = '2rem', color = COLOR.BLACK }: IconProps) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 32 32"
      fill={color}
      width={width}
      height={height}
      aria-labelledby="login-icon-title login-icon-desc"
    >
      <title id="login-icon-title">로그인</title>
      <desc id="login-icon-desc">화살표가 사각형으로 들어가는 아이콘</desc>
      <path d="M14.39,30.5A6.38,6.38,0,0,1,8,24.12v-.25a2.75,2.75,0,1,1,5.49,0v.25a.88.88,0,0,0,.88.89h10.7a.89.89,0,0,0,.89-.89V7.88A.89.89,0,0,0,25.09,7H14.39a.88.88,0,0,0-.88.89v.28A2.75,2.75,0,1,1,8,8.16V7.88A6.38,6.38,0,0,1,14.39,1.5h10.7a6.38,6.38,0,0,1,6.38,6.38V24.12a6.38,6.38,0,0,1-6.38,6.38Z" />
      <path d="M16.72,22a1.65,1.65,0,0,1-1.58-1.71v-1H3.28A2.29,2.29,0,0,1,1,17V15A2.28,2.28,0,0,1,3.28,12.7H15.14v-1A1.64,1.64,0,0,1,16.72,10a1.52,1.52,0,0,1,1.18.58l3.6,4.33a1.77,1.77,0,0,1,0,2.26l-3.6,4.34A1.53,1.53,0,0,1,16.72,22Z" />
    </svg>
  );
};

export default LoginIcon;
