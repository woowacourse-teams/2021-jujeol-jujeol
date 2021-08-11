import styled from '@emotion/styled';
import { css, keyframes } from '@emotion/react';
import { COLOR } from 'src/constants';

const blinkEffect = keyframes`
  50% {
    opacity: 0.5;
  }
`;

const Container = styled.div`
  position: relative;
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

const PreferenceSection = styled.section<{ isBlinked: boolean }>`
  margin-bottom: 3rem;
  padding: 1rem 0;

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

  h2 {
    margin-bottom: 0.5rem;
    font-size: 1.5rem;
    font-weight: bold;
    line-height: 1.25;
  }

  > p {
    margin-bottom: 2rem;
    line-height: 1.25;
  }

  ul {
    display: grid;
    grid-template-columns: repeat(2, auto);
    grid-column-gap: 2rem;
    justify-content: center;
  }
`;

export { Container, Section, PreferenceSection, Image, DescriptionSection };
