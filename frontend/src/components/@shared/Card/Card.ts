import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Card = styled.div<Omit<React.CSSProperties, 'translate'>>`
  background-color: ${({ backgroundColor }) => backgroundColor ?? COLOR.WHITE};
  color: ${({ color }) => color ?? COLOR.BLACK};
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  padding: ${({ padding }) => padding};
  border: ${({ border }) => border};
  border-radius: 1rem;
  position: relative;

  ${({ flexDirection }) => Flex({ flexDirection: flexDirection ?? 'column' })}
`;

export default Card;
