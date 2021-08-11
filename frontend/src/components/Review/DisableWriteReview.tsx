import { MouseEventHandler } from 'react';

import Card from '../@shared/Card/Card';
import { Container } from './DisableWriteReview.styles';
import { COLOR } from 'src/constants';
import Arrow from '../@shared/Arrow/Arrow';

interface Props {
  onMoveToPreferenceSection: MouseEventHandler<HTMLButtonElement>;
}

const DisableWriteReview = ({ onMoveToPreferenceSection }: Props) => {
  return (
    <Container>
      <Card
        height="8.26rem"
        padding="1rem"
        border={`0.5px solid ${COLOR.WHITE_200}`}
        backgroundColor={`${COLOR.PURPLE_600}55`}
      >
        <p>
          먼저 <strong>선호도</strong>를 입력해주세요!
        </p>
        <button type="button" onClick={onMoveToPreferenceSection}>
          선호도 입력하러 가기
          <Arrow size="0.5rem" dir="UP" color={`${COLOR.BLACK_900}`} />
        </button>
      </Card>
    </Container>
  );
};

export default DisableWriteReview;
