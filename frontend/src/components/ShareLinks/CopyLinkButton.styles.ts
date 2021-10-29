import styled from '@emotion/styled';
import { SerializedStyles } from '@emotion/utils';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

export const Container = styled.div<{ css: SerializedStyles }>`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  font-size: 0.8rem;
  color: ${COLOR.BLACK};

  > button {
    cursor: copy;
  }

  svg {
    height: 1rem;
  }

  > input {
    // input이 화면에 보이지 않으면 select할 범위를 찾지 못하는 이슈로 인해, 화면 바깥으로 숨김
    position: fixed;
    top: -20rem;
    right: -20rem;
  }

  ${({ css }) => css};
`;
