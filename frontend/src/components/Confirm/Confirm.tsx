import { KeyboardEventHandler, useEffect, useRef } from 'react';
import { css } from '@emotion/react';

import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import Heading from '../@shared/Heading/Heading';
import { ButtonWrapper, Content, Wrapper } from './Confirm.styles';

interface Props {
  isOpened: boolean;
  message: string;
  subMessage?: string;
  onConfirm: () => void;
  onCancel: () => void;
  direction?: 'default' | 'reverse';
}

const Confirm = ({
  isOpened,
  message,
  subMessage,
  onConfirm,
  onCancel,
  direction = 'default',
}: Props) => {
  const confirmButtonElement = useRef<HTMLButtonElement>(null);
  const cancelButtonElement = useRef<HTMLButtonElement>(null);

  useEffect(() => {
    if (isOpened && confirmButtonElement.current) {
      confirmButtonElement.current.focus();
    }
  }, [isOpened]);

  const focusConfirmButton: KeyboardEventHandler<HTMLButtonElement> = (event) => {
    event.preventDefault();

    if (event.key === 'Tab') {
      confirmButtonElement.current?.focus();
    }
  };

  const focusCancelButton: KeyboardEventHandler<HTMLButtonElement> = (event) => {
    event.preventDefault();

    if (event.key === 'Tab') {
      cancelButtonElement.current?.focus();
    }
  };

  return (
    <Wrapper isOpened={isOpened}>
      <Card width="90%">
        <Content aria-live="assertive">
          <Heading.level3
            color={COLOR.BLACK}
            css={css`
              margin-bottom: 1rem;

              text-align: center;

              line-break: auto;
              word-break: keep-all;
            `}
          >
            {message}
          </Heading.level3>
          {!!subMessage && <p>{subMessage}</p>}
          <ButtonWrapper>
            {direction === 'reverse' ? (
              <>
                <button
                  type="button"
                  onClick={onConfirm}
                  ref={confirmButtonElement}
                  onKeyDown={focusCancelButton}
                >
                  확인
                </button>
                <button
                  type="button"
                  onClick={onCancel}
                  ref={cancelButtonElement}
                  onKeyDown={focusConfirmButton}
                >
                  취소
                </button>
              </>
            ) : (
              <>
                <button
                  type="button"
                  onClick={onCancel}
                  ref={cancelButtonElement}
                  onKeyDown={focusConfirmButton}
                >
                  취소
                </button>
                <button
                  type="button"
                  onClick={onConfirm}
                  ref={confirmButtonElement}
                  onKeyDown={focusCancelButton}
                >
                  확인
                </button>
              </>
            )}
          </ButtonWrapper>
        </Content>
      </Card>
    </Wrapper>
  );
};

export default Confirm;
export type { Props as ConfirmProps };
