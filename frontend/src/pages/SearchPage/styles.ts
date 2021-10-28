import styled from '@emotion/styled';

import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.div`
  width: 100%;
`;

const Categories = styled.section`
  padding: 0 1.2rem;
  margin: 1rem 0 2rem;
  width: 100%;

  ul {
    margin: 0 auto;
  }
`;

const CategoryItem = styled.li`
  width: 4.5rem;
  height: 4.5rem;
  padding: 0 0.5rem;

  background-color: ${COLOR.GRAY_100};
  border-radius: 50%;

  a {
    width: 100%;
    height: 100%;

    ${Flex({ flexDirection: 'column', alignItems: 'center', justifyContent: 'center' })}
  }

  span {
    margin: 0.2rem 0;

    font-size: 0.8rem;
    font-weight: 700;
    color: ${COLOR.BLACK};
  }
`;

export { Categories, CategoryItem, Container };
