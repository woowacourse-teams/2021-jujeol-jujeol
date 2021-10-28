import styled from '@emotion/styled';

import LineClamp from 'src/styles/LineClamp';

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  margin-left: 1rem;

  font-size: 0;
`;

const Title = styled.p`
  font-weight: 600;
  font-size: 1rem;

  + span {
    font-size: 0.7rem;
  }
`;

const Content = styled.div<{ isContentOpen: boolean }>`
  margin-top: 0.3rem;

  position: relative;

  font-size: 0.9rem;
  text-align: justify;
  white-space: break-spaces;
  word-break: break-word;

  ${({ isContentOpen }) => !isContentOpen && LineClamp({ lineClamp: 3 })}
`;

export { Content, TextContainer, Title };
