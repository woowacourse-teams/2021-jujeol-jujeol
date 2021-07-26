import styled from '@emotion/styled';

const TextContainer = styled.div`
  width: 100%;
  height: 100%;
  font-size: 0;
  margin-left: 1rem;

  position: relative;

  button {
    position: absolute;
    right: -1.2rem;
    bottom: 0.1rem;
  }

  > div {
    position: relative;
    margin-right: -0.1rem;

    button {
      border: 1px solid red;
      position: absolute;
      top: -1rem;
      background-color: transparent;
      border: none;
    }
  }
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
  margin-top: 0.3rem;

  font-size: 0.9rem;
  line-height: 1.5;

  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export { TextContainer, Title, Content };
