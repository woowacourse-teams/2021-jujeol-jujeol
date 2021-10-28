import { useContext, useRef } from 'react';
import { useLocation } from 'react-router';
import { css as emotionCss } from '@emotion/react';
import { SerializedStyles } from '@emotion/utils';

import { COLOR, MESSAGE } from 'src/constants';
import { LinkIcon } from '../@Icons';
import IconButton from '../@shared/Button/IconButton';
import { SnackbarContext } from '../@shared/Snackbar/SnackbarProvider';
import { Container } from './CopyLinkButton.styles';

const HOST_URL = 'http://jujeol-jujeol.com';

const CopyLinkButton = ({ css }: { css: SerializedStyles }) => {
  const location = useLocation();

  const targetLink = HOST_URL + location.pathname;
  const inputRef = useRef<HTMLInputElement>(null);

  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const copyLink = async () => {
    const clipboard = navigator.clipboard;

    inputRef.current?.select();
    inputRef.current?.setSelectionRange(0, targetLink.length);

    try {
      await clipboard.writeText(targetLink);

      setSnackbarMessage?.({ type: 'CONFIRM', message: MESSAGE.COPY_LINK_SUCCESS });
    } catch (error) {
      setSnackbarMessage?.({ type: 'ERROR', message: MESSAGE.COPY_LINK_FAILED });
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
      <input type="text" ref={inputRef} value={targetLink} readOnly />
    </Container>
  );
};

export default CopyLinkButton;
