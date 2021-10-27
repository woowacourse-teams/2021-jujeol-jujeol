import { useLocation } from 'react-router-dom';

import { DizzyEmojiColorIcon } from 'src/components/@Icons';
import { APPLICATION_ERROR_CODE, PATH } from 'src/constants';
import { Container, LinkButton, SurveyLink } from './styles';

const ErrorPage = () => {
  const location = useLocation<{ code: number }>();
  const code = location.state?.code ?? 0;

  return (
    <Container>
      <DizzyEmojiColorIcon width="128px" height="128px" />
      <h3>
        <p>
          {code === APPLICATION_ERROR_CODE.NETWORK_ERROR
            ? '서버에 연결할 수 없습니다.'
            : '오류가 발생했습니다.'}
        </p>
        <p>잠시후에 다시 시도해주세요.</p>
      </h3>
      <p>문의 : jujeol2021@gmail.com</p>

      {code === APPLICATION_ERROR_CODE.INTERNAL_SERVER_ERROR && (
        <LinkButton to={PATH.ROOT}>메인화면으로 돌아가기</LinkButton>
      )}

      <SurveyLink
        href="https://forms.gle/haUKZu3tNg1znfcZ7"
        target="_blank"
        rel="noopener noreferrer"
      >
        🚨 불편사항 신고 및 기타문의 작성하기
      </SurveyLink>
    </Container>
  );
};

export default ErrorPage;
