import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';
import LineClamp from 'src/styles/LineClamp';

const Header = styled.div`
  text-align: center;
  margin-bottom: 1rem;
  line-height: 1.25;

  flex-grow: 1;
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}
`;

const Description = styled.div`
  width: 100%;
  ${Flex({ flexDirection: 'column', justifyContent: 'space-between', rowGap: '0.4rem' })}
  margin-left: 1rem;

  > p {
    font-weight: 600;
    width: 100%;
    ${LineClamp({ lineClamp: 1 })}
  }

  > div {
    > span {
      margin-left: 0.3rem;
    }
  }

  button {
    background-color: ${COLOR.YELLOW_300};
    border: 0;
    border-radius: 0.4rem;
    padding: 0.2rem 0.3rem;
    width: fit-content;
    align-self: flex-end;
  }
`;

export { Header, Description };
