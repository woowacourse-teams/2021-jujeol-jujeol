import { useEffect } from 'react';
import { useLocation } from 'react-router';
import { css as emotionCss } from '@emotion/react';
import { SerializedStyles } from '@emotion/utils';

import { COLOR } from 'src/constants';
import { KakaoIcon } from '../@Icons';
import IconButton from '../@shared/Button/IconButton';
import { Container } from './CopyLinkButton.styles';

const JS_KEY = process.env.SNOWPACK_PUBLIC_KAKAO_JS_KEY;
const TEMPLATE_ID = Number(process.env.SNOWPACK_PUBLIC_KAKAO_SHARE_TEMPLATE_ID);

const KakaoShareButton = ({
  content,
  css,
}: {
  content: { title: string; description: string; imageUrl: string };
  css: SerializedStyles;
}) => {
  const location = useLocation();
  const kakao = window.Kakao;

  const shareWithKakao = async () => {
    kakao.Link.sendCustom({
      templateId: TEMPLATE_ID,
      templateArgs: {
        TITLE: content.title,
        DESCRIPTION: content.description,
        IMAGE_URL: content.imageUrl,
        PATH: location.pathname.substring(1),
      },
    });
  };

  useEffect(() => {
    if (!kakao.isInitialized()) {
      kakao.init(JS_KEY);
    }
  }, []);

  return (
    <Container css={css}>
      <IconButton
        id="copy-url-button"
        size="SMALL"
        margin="0 0 0.3rem"
        css={emotionCss`
          padding: 0.6rem;

          background-color: ${COLOR.YELLOW_300};
          border-radius: 50%;
        `}
        onClick={shareWithKakao}
      >
        <KakaoIcon />
      </IconButton>
      <label htmlFor="copy-url-button">
        카카오톡
        <br />
        공유하기
      </label>
    </Container>
  );
};

export default KakaoShareButton;
