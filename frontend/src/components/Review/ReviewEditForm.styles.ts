import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Form = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 90%;
  width: 100%;

  h2 {
    width: 100%;
    font-weight: 700;
    font-size: 1.5rem;
    margin-bottom: 1rem;
    white-space: break-spaces;
    text-align: left;
  }
`;

const Content = styled.div`
  width: 100%;
  height: 70%;
  flex-grow: 1;
  position: relative;
  margin: 2rem 0 1.5rem;

  div {
    position: absolute;
    font-size: 0.75rem;
    top: -1rem;
    right: 0;
    color: ${COLOR.GRAY_300};
    width: 100%;
    display: flex;
    justify-content: space-between;
    padding: 0 0.3rem;
  }

  textarea {
    margin-top: 0.5rem;
    font-size: 1rem;
    width: 100%;
    height: 100%;
    resize: none;
    background-color: ${COLOR.WHITE_200};
    border: none;
    border-radius: 12px;
    padding: 1rem;
  }
`;

const EditButton = styled.button`
  width: 100%;
  height: 3rem;
  border-radius: 0.75rem;
  border: none;
  background-color: ${COLOR.YELLOW_300};
`;

const DeleteButton = styled.button`
  display: block;
  margin-left: auto;
  height: 3rem;
  background-color: transparent;
  border: none;
  color: ${COLOR.GRAY_300};
`;

export { Form, Content, EditButton, DeleteButton };
