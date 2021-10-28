import { Link } from 'react-router-dom';

import { DizzyEmojiColorIcon } from 'src/components/@Icons';
import { PATH } from 'src/constants';
import { Container } from './styles';

const NonExistencePage = () => {
  return (
    <Container>
      <DizzyEmojiColorIcon width="128px" height="128px" />
      <h3>
        <p>존재하지 않는 페이지입니다.</p>
      </h3>

      <Link to={PATH.ROOT}>메인화면으로 돌아가기</Link>
    </Container>
  );
};

export default NonExistencePage;
