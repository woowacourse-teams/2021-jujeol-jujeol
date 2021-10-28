import styled from '@emotion/styled';
import { SerializedStyles } from '@emotion/utils';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';
import { hiddenStyle } from 'src/styles/hidden';

export const Container = styled.div<{ css: SerializedStyles }>`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  font-size: 0.8rem;
  color: ${COLOR.BLACK};

  > button {
    cursor: copy;
  }

  > input {
    ${hiddenStyle};
  }

  ${({ css }) => css};
`;
