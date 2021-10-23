import { css } from '@emotion/react';
import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import Heading from '../@shared/Heading/Heading';
import { Wrapper, Content, ButtonWrapper } from './Confirm.styles';

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
  return (
    <Wrapper isOpened={isOpened}>
      <Card width="90%">
        <Content>
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
                <button type="button" onClick={onConfirm}>
                  확인
                </button>
                <button type="button" onClick={onCancel}>
                  취소
                </button>
              </>
            ) : (
              <>
                <button type="button" onClick={onCancel}>
                  취소
                </button>
                <button type="button" onClick={onConfirm}>
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
