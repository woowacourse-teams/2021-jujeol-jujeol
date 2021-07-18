import styled from '@emotion/styled';

const Wrapper = styled.div`
  width: 100%;
  max-width: 280px;
  height: 3rem;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  position: relative;

  input {
    width: 100%;
    height: 100%;
    z-index: 2;
    position: absolute;
    left: -1rem;
    top: 0;

    cursor: pointer;
    margin: 0;
    height: 3rem;
    appearance: none;
    opacity: 0;
    outline: 0;
  }

  input[type='range']::-webkit-slider-runnable-track {
    width: 100%;
    height: 3rem;
    cursor: pointer;
    background-color: grey;
  }

  div {
    width: 100%;
    display: flex;
    justify-content: space-between;
  }
`;

export { Wrapper };
