import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import LineClamp from 'src/styles/LineClamp';

const Header = styled.div`
  display: flex;
  margin-bottom: 0.5rem;

  button {
    margin-left: auto;
    border: none;
    background-color: transparent;

    :focus {
      border: 1px solid ${COLOR.GRAY_200};
    }
  }
`;

const ReviewerInfo = styled.div`
  display: flex;
  flex-direction: column;
  text-align: left;
  margin-left: 0.5rem;

  span {
    font-size: 1rem;
    font-weight: 600;
  }

  time {
    font-size: 0.75rem;
    font-weight: 400;
  }
`;

const Content = styled.p<{ isContentOpen: boolean }>`
  text-align: justify;
  white-space: break-spaces;

  ${({ isContentOpen }) => !isContentOpen && LineClamp({ lineClamp: 3 })}
`;

const ShowMoreButton = styled.button`
  position: absolute;
  height: 2rem;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: transparent;
  cursor: pointer;
  border: 0;

  :after {
    content: ' ';
    display: inline-block;
    position: absolute;
    right: 0.9rem;
    bottom: 1.4rem;

    width: 0.5rem;
    height: 0.5rem;
    border-top: 2px solid ${COLOR.GRAY_300};
    border-right: 2px solid ${COLOR.GRAY_300};
    transform: rotate(135deg);
  }
`;

export { Header, ReviewerInfo, ShowMoreButton, Content };
