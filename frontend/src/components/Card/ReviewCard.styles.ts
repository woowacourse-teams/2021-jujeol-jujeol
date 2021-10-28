import styled from '@emotion/styled';

import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Header = styled.div`
  margin-bottom: 0.5rem;

  ${Flex({})}

  button {
    padding: 0.3rem;
  }
`;

const ReviewerInfo = styled.div`
  margin-left: 0.5rem;

  flex-grow: 1; // 특정 닉네임 개행 방지 (ex. 진달래_001)
  ${Flex({ flexDirection: 'column' })}

  text-align: left;

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
    width: 0.5rem;
    height: 0.5rem;

    display: inline-block;
    position: absolute;
    right: 0.9rem;
    bottom: 1.4rem;
  }
`;

export { Content, Header, ReviewerInfo, ShowMoreButton };
