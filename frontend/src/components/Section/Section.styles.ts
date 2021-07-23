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

  h2 {
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.8;
  }
  p {
    color: ${COLOR.GRAY_200};
  }

  a {
    color: ${COLOR.GRAY_200};

    :hover {
      text-decoration: underline;
    }
  }
`;

export { Title };
