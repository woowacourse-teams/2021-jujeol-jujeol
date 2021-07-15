import React from 'react';
import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Container = styled.div<Omit<React.CSSProperties, 'translate'>>`
  background-color: ${({ backgroundColor }) => backgroundColor ?? COLOR.WHITE_100};
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  padding: ${({ padding }) => padding};
  border-radius: 1rem;
  position: relative;
`;

export { Container };
