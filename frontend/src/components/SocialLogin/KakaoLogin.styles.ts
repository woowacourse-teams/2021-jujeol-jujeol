import styled from '@emotion/styled';

const KAKAO_BUTTON_COLOR = '#fee500';

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

export { KakaoLoginButton };
