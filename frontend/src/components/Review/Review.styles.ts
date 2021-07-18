import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Wrapper = styled.section`
  line-height: 1.25;
  color: ${COLOR.BLACK_900};

  h2 {
    color: ${COLOR.WHITE_100};
    font-weight: 700;
    text-align: left;
    font-size: 1.25rem;
    margin-bottom: 0.2rem;
    padding-left: 0.75rem;
  }
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  text-align: left;
  padding-bottom: 2rem;

  > div {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    line-height: 1.5;

    margin-bottom: 0.5rem;
    border-bottom: 0.1rem solid ${COLOR.GRAY_200};

    span:first-child {
      font-size: 1.125rem;
    }

    span:last-child {
      font-size: 0.7rem;
      color: ${COLOR.GRAY_300};
    }
  }

  span {
    font-size: 1rem;
    font-weight: 600;
  }

  time {
    font-size: 0.75rem;
    font-weight: 400;
  }

  textarea {
    height: 4rem;
    resize: none;
    background-color: transparent;
    border: none;

    :focus {
      border: 1px solid ${COLOR.GRAY_200};
    }
  }

  button {
    width: 100%;
    position: absolute;
    left: 0;
    bottom: 0;
    height: 2rem;
    background-color: ${COLOR.YELLOW_300};
    border: none;
    border-bottom-left-radius: 0.75rem;
    border-bottom-right-radius: 0.75rem;

    :focus {
      filter: brightness(0.9);
    }
  }
`;

const ReviewList = styled.ul`
  display: grid;
  grid-row-gap: 1rem;
  font-size: 0.875rem;
  margin-top: 1rem;
  line-break: anywhere;

  li {
    position: relative;
  }
`;

export { Wrapper, Form, ReviewList };
