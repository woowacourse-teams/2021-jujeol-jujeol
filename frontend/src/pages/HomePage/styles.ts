import styled from '@emotion/styled';

import { COLOR } from 'src/constants';

const PreferenceRate = styled.div<{ type: 'MY' | 'AVG' | 'EXPECTED' }>`
  padding: 0.3rem 0;

  color: ${({ type }) => (type === 'MY' ? COLOR.VIOLET_100 : COLOR.YELLOW_300)};
  font-size: 0.8rem;

  svg {
    margin: 0 0.2rem;

    stroke: ${({ type }) => (type === 'MY' ? COLOR.VIOLET_100 : COLOR.YELLOW_300)};

    path {
      fill: ${({ type }) => (type === 'MY' ? COLOR.VIOLET_100 : COLOR.YELLOW_300)};
    }
  }
`;

export { PreferenceRate };
