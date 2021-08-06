import styled from '@emotion/styled';
import Flex from 'src/styles/Flex';

const NotificationSection = styled.section`
  padding: 7rem 2rem;

  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}
  row-gap: 2rem;

  svg {
    width: 5rem;
    height: 5rem;
  }

  h2 {
    font-size: 1rem;
    text-align: center;
    font-weight: 600;
  }
`;

const Item = styled.div`
  > p {
    font-size: 0.8rem;
    margin-top: 0.5rem;
    line-height: 1.25;
    text-align: center;

    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;

export { NotificationSection, Item };
