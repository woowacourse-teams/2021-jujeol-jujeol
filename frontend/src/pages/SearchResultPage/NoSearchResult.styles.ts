import styled from '@emotion/styled';

import { FONT_WEIGHT } from 'src/constants';
import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const NotificationSection = styled.section`
  padding: 7rem 2rem;

  ${Flex({
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    rowGap: '2rem',
  })}

  svg {
    width: 5rem;
    height: 5rem;
  }

  p {
    font-size: 1rem;
    text-align: center;
    font-weight: ${FONT_WEIGHT.BOLD};
  }
`;

const Item = styled.div`
  p {
    font-size: 0.8rem;
    margin-top: 0.5rem;
    text-align: center;

    ${LineClamp({ lineClamp: 2 })}
  }
`;

export { Item, NotificationSection };
