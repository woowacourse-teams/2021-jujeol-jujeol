import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Item = styled.li`
  width: 100%;
  height: fit-content;
  border-radius: 0.5rem;

  display: flex;

  img {
    width: 88px;
    background-color: ${COLOR.WHITE_200};
    border-radius: 0.5rem;
    object-fit: cover;
  }

  :hover {
    background-color: ${COLOR.PURPLE_400};
  }
`;

const ItemInfo = styled.div`
  padding: 0.5rem 1rem;

  h3 {
    font-size: 1.25rem;
    font-weight: 600;
    line-height: 1.5;

    overflow: hidden;
    display: -webkit-box;

    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;

    line-height: 1.3;
    margin-bottom: 0.3rem;
  }

  p {
    font-size: 0.9rem;
    color: ${COLOR.GRAY_200};
  }
`;

export { Item, ItemInfo };
