import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Item = styled.li`
  width: 100%;
  height: 88px;
  border-radius: 0.5rem;

  a {
    width: 100%;
    height: 88px;

    ${Flex({ alignItems: 'center' })}
  }

  img {
    width: 88px;
    height: 88px;
    background-color: ${COLOR.GRAY_100};
    border-radius: 0.5rem;
    object-fit: cover;
  }

  :hover,
  :focus-within {
    background-color: ${COLOR.PURPLE_600};
  }
`;

const ItemInfo = styled.div`
  padding: 0.5rem 1rem;

  p {
    font-size: 0.9rem;
    color: ${COLOR.GRAY_200};
  }
`;

export { Item, ItemInfo };
