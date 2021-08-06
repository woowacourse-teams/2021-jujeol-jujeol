import styled from '@emotion/styled';
import { COLOR, Z_INDEX } from 'src/constants';
import LineClamp from 'src/styles/LineClamp';

const ItemImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: inherit;
`;

const ItemInfo = styled.div`
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: ${Z_INDEX.ITEM_TITLE};
  padding: 2rem 1rem 1rem;

  background: linear-gradient(transparent, rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.7));

  h3 {
    font-size: 1.25rem;
    font-weight: 800;
    line-height: 1.3;
    margin-bottom: 0.3rem;
    ${LineClamp({ lineClamp: 2 })}
  }

  p {
    font-size: 0.9rem;
    color: ${COLOR.GRAY_200};
  }
`;

export { ItemImage, ItemInfo };
