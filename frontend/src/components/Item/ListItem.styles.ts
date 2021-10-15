import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Item = styled.li`
  width: 100%;
  height: 88px;
  border-radius: 0.5rem;

  ${Flex({ alignItems: 'center' })}

  img {
    width: 88px;
    height: 88px;
    background-color: ${COLOR.GRAY_100};
    border-radius: 0.5rem;
    object-fit: cover;
  }

  :hover {
    background-color: ${COLOR.PURPLE_600};
  }
`;

const ItemInfo = styled.div`
  padding: 0.5rem 1rem;

  h3 {
    font-size: 1rem;
    font-weight: 600;
    margin-bottom: 0.3rem;

    ${LineClamp({ lineClamp: 2 })}
  }

  p {
    font-size: 0.9rem;
    color: ${COLOR.GRAY_200};
  }
`;

export { Item, ItemInfo };
