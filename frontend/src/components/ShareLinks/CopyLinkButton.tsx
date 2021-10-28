import { useContext, useRef } from 'react';
import { useLocation } from 'react-router';
import { css as emotionCss } from '@emotion/react';
import { SerializedStyles } from '@emotion/utils';

import { COLOR } from 'src/constants';
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
    inputRef.current?.setSelectionRange(0, 9999);

    try {
      await clipboard.writeText(targetLink);

      setSnackbarMessage?.({ type: 'CONFIRM', message: '클립보드에 복사되었습니다.' });
    } catch (error) {
      setSnackbarMessage?.({ type: 'ERROR', message: '복사에 실패했습니다.' });
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
