import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const PreferenceRate = styled.div<{ type: 'MY' | 'AVG' | 'EXPECTED' }>`
  color: ${({ type }) => (type === 'MY' ? COLOR.PURPLE_300 : COLOR.YELLOW_300)};
  padding: 0.2rem 0;
  font-size: 0.8rem;

  svg {
    margin: 0 0.2rem;

    path {
      fill: ${COLOR.YELLOW_300};
    }
  }
`;

export { PreferenceRate };
