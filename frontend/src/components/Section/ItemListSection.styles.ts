import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Title = styled.div<Omit<React.CSSProperties, 'translate'>>`
  margin: 0 0 2rem;
  text-align: ${({ textAlign }) => textAlign || 'left'};
  padding: 0 2rem;

  h2 {
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.8;
  }
  p {
    color: ${COLOR.GRAY_200};
  }
`;
export { Title };
