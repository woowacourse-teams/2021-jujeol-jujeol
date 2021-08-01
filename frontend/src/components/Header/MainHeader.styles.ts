import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const envContent =
  process.env.SNOWPACK_PUBLIC_ENV === 'PROD'
    ? 'beta'
    : process.env.SNOWPACK_PUBLIC_ENV?.toLowerCase();

const Logo = styled.h1`
  font-size: 1.4rem;
  font-weight: 700;
  color: ${COLOR.WHITE_100};
  text-align: center;
  position: relative;

  :after {
    content: '${envContent}';
    font-size: 0.8rem;
    padding: 0.2rem;
    position: absolute;
    left: 100%;
    top: 0;
  }
`;

export { Logo };
