import Card from '../@shared/Card/Card';
import { Wrapper, Content, ButtonWrapper } from './Confirm.styles';

interface Props {
  message: string;
  subMessage: string;
  onConfirm: () => void;
  onCancel: () => void;
}

const Confirm = ({ message, subMessage, onConfirm, onCancel }: Props) => {
  return (
    <Wrapper>
      <Card width="90%">
        <Content>
          <h3>{message}</h3>
          <p>{subMessage}</p>
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
