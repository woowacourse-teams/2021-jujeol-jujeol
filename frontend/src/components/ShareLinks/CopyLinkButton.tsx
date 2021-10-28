import { useContext, useRef } from 'react';
import { useLocation } from 'react-router';
import { css as emotionCss } from '@emotion/react';
import { SerializedStyles } from '@emotion/utils';

import { COLOR, MESSAGE } from 'src/constants';
import { LinkIcon } from '../@Icons';
import IconButton from '../@shared/Button/IconButton';
import { SnackbarContext } from '../@shared/Snackbar/SnackbarProvider';
import { Container } from './CopyLinkButton.styles';

const HOST_URL = process.env.SNOWPACK_PUBLIC_HOST_URL;

const CopyLinkButton = ({ css }: { css: SerializedStyles }) => {
  const inputRef = useRef<HTMLInputElement>(null);

  const location = useLocation();

  const targetLink = HOST_URL + location.pathname;

  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const copyLink = async () => {
    const clipboard = navigator.clipboard;

    try {
      await clipboard.writeText(targetLink);

      setSnackbarMessage?.({ type: 'CONFIRM', message: MESSAGE.COPY_LINK_SUCCESS });
    } catch (error) {
      try {
        // 카카오톡 인앱 브라우저에서 clipboard 미적용으로 인한 지원 하지 않음 에러 발생
        inputRef.current?.select();
        inputRef.current?.setSelectionRange(0, targetLink.length);

        document.execCommand('Copy');
        setSnackbarMessage?.({ type: 'CONFIRM', message: MESSAGE.COPY_LINK_SUCCESS });
      } catch (error) {
        setSnackbarMessage?.({ type: 'ERROR', message: MESSAGE.COPY_LINK_FAILED });
      }
    }
  };

  return (
    <Container css={css}>
      <IconButton
        id="copy-url-button"
        size="SMALL"
        margin="0 0 0.2rem"
        css={emotionCss`
          padding: 0.6rem;

          background-color: ${COLOR.PURPLE_400};
          border-radius: 50%;
        `}
        onClick={copyLink}
      >
        <LinkIcon />
      </IconButton>
      <label htmlFor="copy-url-button">URL 복사</label>
      <input ref={inputRef} type="text" value={targetLink} readOnly />
    </Container>
  );
};

export default CopyLinkButton;
