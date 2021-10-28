import { css } from '@emotion/react';

import { COLOR } from 'src/constants';
import { hiddenStyle } from 'src/styles/hidden';
import GoBackButton from '../@shared/Button/GoBackButton';
import Heading from '../@shared/Heading/Heading';
import { NavHeader } from './NavigationHeader.styles';

interface Props {
  title: string;
}

const NavigationHeader = ({ title }: Props) => {
  return (
    <NavHeader>
      <GoBackButton
        color={COLOR.WHITE}
        css={css`
          padding: 0.5rem;

          background-color: transparent;
          border: 0;
          margin-left: 0.5rem;
        `}
      />
      <Heading.level1 css={hiddenStyle}>주절주절</Heading.level1>
      <Heading.level2
        css={css`
          margin-top: 0.2rem;
        `}
      >
        {title}
      </Heading.level2>
    </NavHeader>
  );
};

export default NavigationHeader;
