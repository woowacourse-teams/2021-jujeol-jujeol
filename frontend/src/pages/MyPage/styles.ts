import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

import { COLOR } from 'src/constants';

const Header = styled.header`
  ${Flex({ alignItems: 'center' })};

  position: relative;

  width: 100%;
  height: 3.5em;

  button {
    background: transparent;
    border: none;
    padding: 0 0.6rem;

    &:after {
      content: '';
      width: 0.7rem;
      height: 0.7rem;
      border-top: 2px solid white;
      border-right: 2px solid white;
      display: inline-block;
      transform: rotate(225deg);
    }
  }

  h2 {
    font-size: 1.3rem;
    font-weight: 700;
  }
`;

const Statistics = styled.ul`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};

  width: 100%;
  height: 5.5rem;

  position: relative;

  background-color: ${COLOR.PURPLE_500};

  ::after {
    content: '';
    height: 3rem;
    border-left: 1px solid ${COLOR.PURPLE_900};

    position: absolute;
  }

  li {
    ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

    width: 100%;
    height: 4rem;

    > p {
      font-size: 1.1rem;
      font-weight: 700;
    }

    > p:last-child {
      font-size: 0.8rem;
      font-weight: 300;
      margin-top: 0.5rem;
    }
  }
`;

const VerticalItem = styled.li`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

  :last-child {
    position: relative;
    &::after {
      content: '';
      position: absolute;
      right: -1.5rem;
      width: 1.5rem;
      height: 100%;
    }
  }

  p {
    margin: 0.5rem 0 0.3rem;
    font-size: 0.95rem;
  }

  div {
    ${Flex({ justifyContent: 'center', alignItems: 'center' })};

    span {
      font-size: 0.8rem;
      margin-left: 0.3rem;
    }
  }
`;

const VerticalScrollList = styled.ul`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })};

  padding-right: 1.3rem;

  li {
    width: 100%;
    margin-bottom: 1rem;

    > div {
      display: flex;
      align-items: center;
    }
  }
`;

export { Header, Statistics, VerticalItem, VerticalScrollList };
