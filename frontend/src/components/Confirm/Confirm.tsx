import Card from '../@shared/Card/Card';
import { Wrapper, Content, ButtonWrapper } from './Confirm.styles';

interface Props {
  isOpened: boolean;
  message: string;
  subMessage?: string;
  onConfirm: () => void;
  onCancel: () => void;
}

const Confirm = ({ isOpened, message, subMessage, onConfirm, onCancel }: Props) => {
  return (
    <Wrapper isOpened={isOpened}>
      <Card width="90%">
        <Content>
          <h3>{message}</h3>
          {!!subMessage && <p>{subMessage}</p>}
          <ButtonWrapper>
            <button type="button" onClick={onConfirm}>
              확인
            </button>
            <button type="button" onClick={onCancel}>
              취소
            </button>
          </ButtonWrapper>
        </Content>
      </Card>
    </Wrapper>
  );
};

export default Confirm;
export type { Props as ConfirmProps };
