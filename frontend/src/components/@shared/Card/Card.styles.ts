import React from 'react';
import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.div<Omit<React.CSSProperties, 'translate'>>`
  background-color: ${({ backgroundColor }) => backgroundColor ?? COLOR.WHITE_100};
  color: ${({ color }) => color ?? COLOR.BLACK_900};
  width: ${({ width }) => width};
  height: ${({ height }) => height};
  padding: ${({ padding }) => padding};
  border: ${({ border }) => border};
  border-radius: 1rem;
  position: relative;

  ${({ flexDirection }) => Flex({ flexDirection: flexDirection ?? 'column' })}
`;

export { Container };
