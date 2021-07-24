import styled from '@emotion/styled';

const PreviewSection = styled.section`
  width: 100%;
  min-height: 10rem;
  padding: 1.3rem;
  padding-right: 0;
  margin: 0.5rem 0;

  div {
    display: flex;
    justify-content: space-between;
    align-items: start;

    h3 {
      font-size: 1.1rem;
      font-weight: 700;
      margin-bottom: 1rem;
    }

    button {
      margin-right: 0.5rem;

      background-color: transparent;
      border: none;
      color: inherit;

      font-size: 0.8rem;
      font-weight: 400;

      &::after {
        content: '';
        width: 0.4rem;
        height: 0.4rem;
        border-top: 1px solid white;
        border-right: 1px solid white;
        display: inline-block;
        transform: rotate(45deg);
      }
    }
  }
`;

export { PreviewSection };
