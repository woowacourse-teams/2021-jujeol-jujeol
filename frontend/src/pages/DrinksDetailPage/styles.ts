import styled from '@emotion/styled';
import { css, keyframes } from '@emotion/react';
import { COLOR } from 'src/constants';

const blinkEffect = keyframes`
  50% {
    opacity: 0;
  }
`;

const Image = styled.img`
  width: 100%;
  background-color: ${COLOR.WHITE_100};
`;

const Section = styled.section`
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;
  background-color: ${COLOR.PURPLE_900};
  transform: translateY(-6rem);
  padding: 2rem 1.5rem;
  text-align: center;
  position: relative;
`;

const PreferenceSection = styled.section<{ isScrolled: boolean }>`
  margin-bottom: 2rem;
  padding: 2rem 0;

  h3 {
    margin-bottom: 0.8rem;
    font-size: 1.3rem;
    font-weight: 400;
    color: ${COLOR.WHITE_200};

    span {
      font-weight: 700;
      font-size: 1.4rem;
    }
  }

  p {
    margin-top: 1rem;
  }

  div {
    ${({ isScrolled }) =>
      isScrolled &&
      css`
        animation: ${blinkEffect} 0.8s step-end 3;
      `}
  }
`;

const DescriptionSection = styled.section`
  margin-bottom: 1.5rem;

  h2 {
    margin-bottom: 0.5rem;
    font-size: 1.5rem;
    font-weight: bold;
  }

  > p {
    margin-bottom: 2rem;
  }

  ul {
    display: grid;
    grid-template-columns: repeat(2, auto);
    grid-column-gap: 2rem;
    justify-content: center;
  }
`;

export { Section, PreferenceSection, Image, DescriptionSection };
