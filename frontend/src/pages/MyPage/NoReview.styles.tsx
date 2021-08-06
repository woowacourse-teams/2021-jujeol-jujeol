import styled from '@emotion/styled';
import { COLOR } from 'src/constants';
import Flex from 'src/styles/Flex';

const Header = styled.div`
  text-align: center;
  margin-bottom: 1rem;
  line-height: 1.25;
`;

const Content = styled.ul`
  ${Flex({ flexDirection: 'column' })}

  row-gap: 1rem;
`;

const Description = styled.div`
  width: 100%;
  ${Flex({ flexDirection: 'column', justifyContent: 'space-between' })}
  row-gap: 0.4rem;
  margin-left: 1rem;

  > p {
    font-weight: 600;
    width: 100%;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
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

export { Header, Content, Description };
