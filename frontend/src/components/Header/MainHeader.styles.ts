import styled from '@emotion/styled';

import Heading from '../@shared/Heading/Heading';

const envContent =
  process.env.SNOWPACK_PUBLIC_ENV === 'PROD'
    ? 'beta'
    : process.env.SNOWPACK_PUBLIC_ENV?.toLowerCase();

const Logo = styled(Heading.level1)`
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
