import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Container = styled.div`
  width: 100%;
`;

const Categories = styled.section`
  padding: 1.2rem;
  margin: 1.5rem 0;
  width: 100%;
  h3 {
    font-size: 1.2rem;
    font-weight: 700;
    margin: 1rem 0.5rem 1.5rem;
  }
  ul {
    margin: 0 auto;
  }
`;

const CategoryItem = styled.li`
  ${Flex({ flexDirection: 'column', alignItems: 'center', justifyContent: 'center' })}
  padding: 0 0.5rem;
  width: 4.5rem;
  height: 4.5rem;
  border-radius: 50%;
  background-color: ${COLOR.WHITE_200};

  span {
    font-size: 0.8rem;
    font-weight: 600;
    color: ${COLOR.BLACK_900};
    margin: 0.2rem 0;
  }
`;

export { Container, Categories, CategoryItem };
