import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Container = styled.div`
  padding: 0 1rem;
`;

const AlertWrapper = styled.div`
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;

  background-color: ${`${COLOR.BLACK_900}aa`};

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  a {
    width: 80%;
    height: 3rem;
    border-radius: 0.8rem;

    ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

    background-color: ${COLOR.PURPLE_300};
    color: ${COLOR.WHITE_200};
    text-align: center;

    margin: 1rem 0;
  }
`;

const DrinkDescription = styled.div`
  margin-left: 1rem;
`;

const Title = styled.p`
  margin-bottom: 0.5rem;
  ${LineClamp({ lineClamp: 2 })}
`;

export { Container, AlertWrapper, DrinkDescription, Title };
