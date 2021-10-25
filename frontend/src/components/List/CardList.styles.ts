import styled from '@emotion/styled';

import Grid from '../@shared/Grid/Grid';

const ItemList = styled(Grid)`
  overflow-x: auto;
  scroll-behavior: smooth;

  -ms-overflow-style: none;
  scrollbar-width: none;

  ::-webkit-scrollbar {
    display: none;
  }

  :before,
  :after {
    content: ' ';
    width: 1rem;
  }
`;

export { ItemList };
