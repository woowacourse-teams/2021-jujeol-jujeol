import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: start;

  margin-left: 1rem;
`;

const Title = styled.p`
  font-weight: 600;
  font-size: 1rem;
  line-height: 1.5;

  + span {
    font-size: 0.7rem;
    line-height: 1.5;
  }
`;

const Content = styled.p`
  font-size: 0.9rem;
  line-height: 1.5;

  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export { Container, Title, Content };
