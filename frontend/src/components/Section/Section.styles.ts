import styled from '@emotion/styled';

import { COLOR } from 'src/constants';

interface TitleProps extends Omit<React.CSSProperties, 'translate'> {
  isShowMoreEnabled?: boolean;
}

const Title = styled.div<TitleProps>`
  margin: 0 0 1rem;
  text-align: ${({ textAlign }) => textAlign || 'left'};
  padding: 0 2rem;
  display: flex;
  justify-content: ${({ isShowMoreEnabled }) => (isShowMoreEnabled ? 'space-between' : 'center')};
  align-items: baseline;

  p {
    color: ${COLOR.GRAY_200};
  }
`;

export { Title };
