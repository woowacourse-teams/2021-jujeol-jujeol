import styled from '@emotion/styled';
import { SerializedStyles } from '@emotion/utils';
import { COLOR, FONT_WEIGHT } from 'src/constants';

type HeadingStyle = React.CSSProperties & { css?: SerializedStyles };

const Heading = {
  level1: styled.h1<HeadingStyle>`
    line-height: 1.5;
    font-weight: ${FONT_WEIGHT.EXTRA_BOLD};
    font-size: 1.5rem;
    color: ${({ color }) => color || COLOR.WHITE};

    ${({ css }) => css};
  `,

  level2: styled.h2<HeadingStyle>`
    line-height: 1.5;
    font-weight: ${FONT_WEIGHT.EXTRA_BOLD};
    font-size: 1.3rem;

    color: ${({ color }) => color || COLOR.WHITE};
    ${({ css }) => css};
  `,

  level3: styled.h3<HeadingStyle>`
    font-weight: ${FONT_WEIGHT.BOLD};
    font-size: 1.1rem;

    color: ${({ color }) => color || COLOR.WHITE};
    ${({ css }) => css};
  `,

  level4: styled.h4<HeadingStyle>`
    font-weight: ${FONT_WEIGHT.BOLD};
    font-size: 1rem;

    color: ${({ color }) => color || COLOR.WHITE};
    ${({ css }) => css};
  `,

  level5: styled.h5<HeadingStyle>`
    font-weight: ${FONT_WEIGHT.NORMAL};
    font-size: 0.85rem;

    color: ${({ color }) => color || COLOR.WHITE};
    ${({ css }) => css};
  `,

  level6: styled.h6<HeadingStyle>`
    font-weight: ${FONT_WEIGHT.NORMAL};
    font-size: 0.75rem;

    color: ${({ color }) => color || COLOR.WHITE};
    ${({ css }) => css};
  `,
};

export default Heading;
