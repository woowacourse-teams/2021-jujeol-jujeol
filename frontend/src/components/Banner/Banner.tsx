import { css } from '@emotion/react';

import Heading from '../@shared/Heading/Heading';
import { Container } from './Banner.styles';

interface Props {
  type: 'IMAGE';
  title: '프로모션 배너';
  src: string;
  alt: string;
}

const Banner = ({ type, title, src, alt }: Props) => {
  return (
    <Container tabIndex={0} aria-label={`광고 : ${alt}`}>
      {type === 'IMAGE' && (
        <>
          <Heading.level2
            css={css`
              opacity: 0;
              position: absolute;
              top: 0;
              left: 0;
            `}
          >
            {title}
          </Heading.level2>
          <img src={src} alt={alt} loading="lazy" />
        </>
      )}
    </Container>
  );
};

export default Banner;
