import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const KAKAO_BUTTON_COLOR = '#fee500';

const Container = styled.div`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;

  padding: 0 1.5rem;

  > div {
    width: 100%;
  }

  p {
    font-size: 1rem;
    color: ${COLOR.GRAY_300};
    margin-bottom: 1rem;
  }
`;

const Logo = styled.h1`
  margin-bottom: 4rem;

  img {
    width: 180px;
  }
`;

const KakaoLoginButton = styled.button`
  width: 100%;
  max-width: 20rem;
  height: 3rem;
  border: 0;
  border-radius: 0.75rem;
  background-color: ${KAKAO_BUTTON_COLOR};
  cursor: pointer;

  svg {
    height: 1rem;
    margin-right: 0.75rem;
  }

  span {
    font-size: 1rem;
    color: rgba(0, 0, 0, 0.85);
    vertical-align: bottom;
  }
`;

export { Container, Logo, KakaoLoginButton };
