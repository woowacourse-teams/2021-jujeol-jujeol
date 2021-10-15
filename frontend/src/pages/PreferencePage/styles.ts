import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Container = styled.div<{ isLoggedIn: boolean }>`
  padding: 0 1rem;

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
    margin: 1rem 0;
  }
`;

const AlertWrapper = styled.div`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  background-color: ${`${COLOR.BLACK}aa`};

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  a {
    width: 80%;
    height: 3rem;
    border-radius: 0.8rem;

    ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

    background-color: ${COLOR.PURPLE_600};
    color: ${COLOR.GRAY_100};
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

  p {
    margin: 2rem 0 1rem;
    font-size: 1rem;
    text-align: center;
    font-weight: 700;
  }

  p:last-child {
    margin: 0;
    font-size: 0.8rem;
    font-weight: 400;
  }
`;

export { Container, Notification, AlertWrapper, DrinkDescription, Title, NoDrink };
