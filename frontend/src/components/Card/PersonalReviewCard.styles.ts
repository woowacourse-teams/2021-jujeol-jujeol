import styled from '@emotion/styled';
import css from '@emotion/css';

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  font-size: 0;
  margin-left: 1rem;

  position: relative;

  > div {
    button {
      border: 1px solid red;
      position: absolute;
      right: -1rem;
      top: -1rem;
      background-color: transparent;
      border: none;
    }
  }
`;

const Title = styled.p`
  font-weight: 600;
  font-size: 1rem;
  line-height: 1.5;

  + span {
    font-size: 0.7rem;
    line-height: 1.5;
  }
`;

const Content = styled.div<{ isContentOpen: boolean }>`
  margin-top: 0.3rem;

  text-align: justify;
  white-space: break-spaces;
  font-size: 0.9rem;
  line-height: 1.5;

  ${({ isContentOpen }) =>
    !isContentOpen &&
    css`
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      text-overflow: ellipsis;
    `}
`;

const ShowMoreButton = styled.button`
  background-color: transparent;
  border: 0;

  position: absolute;
  right: -1rem;
  bottom: 0.1rem;
`;

export { TextContainer, Title, Content, ShowMoreButton };
