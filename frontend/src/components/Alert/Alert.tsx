import Card from '../@shared/Card/Card';
import { Wrapper, Content } from './Alert.styles';

const Alert = ({ message, onClose }: { message: string; onClose: () => void }) => {
  return (
    <Wrapper>
      <Card>
        <Content>
          <h3>알림</h3>
          <p>{message}</p>
          <button type="button" onClick={onClose}>
            닫기
          </button>
        </Content>
      </Card>
    </Wrapper>
  );
};

export default Alert;
