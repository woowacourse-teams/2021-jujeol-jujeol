import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Container = styled.div<{ isLoggedIn: boolean }>`
  padding: 1rem;

  ${({ isLoggedIn }) =>
    !isLoggedIn &&
    css`
      height: calc(6.5rem * 5);
      overflow: hidden;
      touch-action: none;

      ul {
        pointer-events: none;
      }
    `}
`;

const Notification = styled.div`
  p {
    font-size: 0.8rem;
    line-height: 1.1;
    margin-bottom: 1rem;
  }
`;

const AlertWrapper = styled.div`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  background-color: ${`${COLOR.BLACK_900}aa`};

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  a {
    width: 80%;
    height: 3rem;
    border-radius: 0.8rem;

    ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

    background-color: ${COLOR.PURPLE_300};
    color: ${COLOR.WHITE_200};
    text-align: center;

    margin: 1rem 0;
  }
`;

const DrinkDescription = styled.div`
  margin-left: 1rem;
  width: 100%;
  max-width: 280px;

  svg {
    width: 20%;
  }
`;

const Title = styled.p`
  margin-bottom: 0.5rem;
  ${LineClamp({ lineClamp: 2 })}
`;

const NoDrink = styled.div`
  padding: 7rem 2rem;
  height: 100%;

  ${Flex({
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  })}

  svg {
    width: 5rem;
    height: 5rem;
  }

  h2 {
    margin: 2rem 0 1rem;
    font-size: 1rem;
    text-align: center;
    font-weight: 600;
    line-height: 1.25;
  }

  p {
    font-size: 0.8rem;
  }
`;

export { Container, Notification, AlertWrapper, DrinkDescription, Title, NoDrink };
