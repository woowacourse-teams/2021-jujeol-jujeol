import styled from '@emotion/styled';
import { COLOR, Z_INDEX } from 'src/constants';

const Title = styled.div<Omit<React.CSSProperties, 'translate'>>`
  margin: 0 0 2rem;
  text-align: ${({ textAlign }) => textAlign || 'left'};

  h2 {
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.8;
  }
  p {
    color: ${COLOR.GRAY_200};
  }
`;

const ItemList = styled.ul<{ count: number }>`
  height: 20rem;
  overflow-x: auto;
  scroll-behavior: smooth;

  -ms-overflow-style: none;
  scrollbar-width: none;

  ::-webkit-scrollbar {
    display: none;
  }

  display: grid;
  grid-template-columns: ${({ count }) => `repeat(${count + 2}, 1fr)`};
  grid-column-gap: 2rem;

  :before,
  :after {
    content: ' ';
    width: 1rem;
  }
`;

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

export { Title, ItemList, ItemImage, ItemInfo };
