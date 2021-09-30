import { MouseEventHandler } from 'react';

import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import Button from '../@shared/Button/Button';
import { Container } from './DisableWriteReview.styles';

interface Props {
  onNoticeToInputPreference: MouseEventHandler<HTMLButtonElement>;
}

const DisableWriteReview = ({ onNoticeToInputPreference }: Props) => {
  return (
    <Container>
      <Card
        height="8.26rem"
        padding="1rem"
        border={`0.5px solid ${COLOR.GRAY_100}`}
        backgroundColor={`${COLOR.PURPLE_600}55`}
      >
        <p>
          먼저 <strong>선호도</strong>를 입력해주세요!
        </p>
        <Button
          type="button"
          shape="ROUND"
          size="SMALL"
          width="fit-content"
          margin="0.75rem 0 0"
          onClick={onNoticeToInputPreference}
        >
          선호도 입력하러 가기
        </Button>
      </Card>
    </Container>
  );
};

export default DisableWriteReview;
