import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const TextWrapper = styled.div`
  margin-left: 0.6rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const Title = styled.p`
  font-weight: 300;
  margin-bottom: 0.2rem;
`;

const Content = styled.p`
  font-size: 1.25rem;
`;

export { Container, Content, TextWrapper, Title };
