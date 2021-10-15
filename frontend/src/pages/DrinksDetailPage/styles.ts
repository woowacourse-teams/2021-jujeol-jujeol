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
  width: 100%;
  padding: 3rem 0;

  background-color: ${COLOR.WHITE};
`;

const Image = styled.img`
  width: 100%;
  height: fit-content;
`;

const Section = styled.section<{ isShowImageFull: boolean }>`
  padding: 2rem 1.5rem;

  position: relative;
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;
  transition: transform 0.1s ease;
  transform: ${({ isShowImageFull }) =>
    isShowImageFull ? 'translateY(-2rem)' : 'translateY(-6rem)'};

  background-color: ${COLOR.PURPLE_900};

  text-align: center;
`;

const PreferenceSection = styled.section<{ isBlinked: boolean }>`
  margin-bottom: 1rem;
  padding: 1rem 0 1.5rem;

  border-bottom: 0.5px solid ${COLOR.GRAY_300};

  div {
    ${({ isBlinked }) =>
      isBlinked &&
      css`
        animation: ${blinkEffect} 0.8s step-end 3;
      `}
  }

  p {
    margin-top: 1rem;
  }
`;

const DescriptionSection = styled.section`
  margin-bottom: 3rem;
  padding-top: 1rem;

  > p {
    margin-bottom: 1.5rem;
  }
`;

const Description = styled.div<{ isShowMore: boolean; isContentOpen: boolean }>`
  margin: 2rem 0;
  position: relative;

  line-break: anywhere;

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

    color: ${COLOR.GRAY_100}dd;
  }
`;

const descriptionButtonStyle = css`
  height: 1.25rem;

  position: absolute;
  right: 0;

  border: none;

  font-size: 0.8rem;
  font-weight: 600;
  color: ${COLOR.PURPLE_100};

  :hover {
    cursor: pointer;
  }
`;

const FoldButton = styled.button`
  height: 2rem;

  bottom: -2rem;

  background-color: ${COLOR.PURPLE_900};

  ${descriptionButtonStyle};
`;

const ShowMoreButton = styled.button`
  padding-left: 2rem;

  bottom: 0;

  background: linear-gradient(
    90deg,
    ${COLOR.PURPLE_900} 0%,
    transparent 0%,
    ${COLOR.PURPLE_900} 40%
  );

  ${descriptionButtonStyle};
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
