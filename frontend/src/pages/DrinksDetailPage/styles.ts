import styled from '@emotion/styled';
import { css, keyframes } from '@emotion/react';

import LineClamp from 'src/styles/LineClamp';
import { COLOR } from 'src/constants';

const blinkEffect = keyframes`
  50% {
    opacity: 0.5;
  }
`;

const Container = styled.div`
  position: relative;
`;

const ImageWrapper = styled.div`
  padding: 3rem 0;
  background-color: ${COLOR.WHITE_100};
`;

const Image = styled.img`
  width: 100%;
  height: fit-content;
`;

const Section = styled.section<{ isShowImageFull: boolean }>`
  position: relative;
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;
  background-color: ${COLOR.PURPLE_900};
  transform: ${({ isShowImageFull }) =>
    isShowImageFull ? 'translateY(-2rem)' : 'translateY(-6rem)'};
  padding: 2rem 1.5rem;

  text-align: center;
`;

const PreferenceSection = styled.section<{ isBlinked: boolean }>`
  margin-bottom: 1rem;
  padding: 1rem 0 1.5rem;
  border-bottom: 0.5px solid ${COLOR.GRAY_300};

  h3 {
    margin-bottom: 0.8rem;
    font-size: 1.3rem;
    font-weight: 400;
    line-height: 1.25;
    color: ${COLOR.WHITE_200};
  }

  div {
    ${({ isBlinked }) =>
      isBlinked &&
      css`
        animation: ${blinkEffect} 0.8s step-end 3;
      `}
  }

  p {
    margin-top: 1rem;
    line-height: 1.25;
  }
`;

const DescriptionSection = styled.section`
  margin-bottom: 3rem;
  padding-top: 1rem;

  h2 {
    margin-bottom: 0.5rem;
    font-size: 1.5rem;
    font-weight: bold;
    line-height: 1.25;
  }

  > p {
    margin-bottom: 1.5rem;
    line-height: 1.25;
  }

  ul {
    display: grid;
    grid-template-columns: repeat(2, auto);
    grid-column-gap: 2rem;
    justify-content: center;
  }
`;

const Description = styled.div<{ isShowMore: boolean; isContentOpen: boolean }>`
  margin: 2rem 0;
  position: relative;

  p {
    ${({ isShowMore }) =>
      isShowMore &&
      css`
        min-height: 3.8rem;
      `}

    ${({ isContentOpen }) => !isContentOpen && LineClamp({ lineClamp: 3 })}

    width: 100%;
    word-break: keep-all;
    word-wrap: break-word;

    line-height: 1.25;
    color: ${COLOR.WHITE_200}dd;
  }
`;

const descriptionButtonStyle = css`
  position: absolute;
  right: 0;
  border: none;
  height: 1.25rem;

  font-weight: 600;
  font-size: 0.8rem;
  color: ${COLOR.PURPLE_100};

  background-color: ${COLOR.PURPLE_900};

  :hover {
    cursor: pointer;
  }
`;

const FoldButton = styled.button`
  ${descriptionButtonStyle};
  height: 2rem;
  bottom: -2rem;
`;

const ShowMoreButton = styled.button`
  ${descriptionButtonStyle};

  right: 0;
  bottom: 0;
  padding-left: 2rem;
  background: linear-gradient(
    90deg,
    rgba(22, 14, 39) 0%,
    rgba(0, 0, 0, 0) 0%,
    rgba(22, 14, 39) 15%
  );
`;

export {
  Container,
  ImageWrapper,
  Image,
  Section,
  PreferenceSection,
  DescriptionSection,
  Description,
  ShowMoreButton,
  FoldButton,
};
