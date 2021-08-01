import Flex from 'src/styles/Flex';
import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Container = styled.form<Omit<React.CSSProperties, 'translate'>>`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};
  width: ${({ width }) => width && `${width}`};
  height: 3rem;
  padding: ${({ padding }) => padding && `${padding}`};
  margin: 1.5rem auto;
  background-color: rgba(255, 255, 255, 0.3);
  border: 2px solid white;
  border-radius: 2rem;
  position: relative;

  button:first-of-type {
    background-color: transparent;
    border: 0;
    margin-left: -1rem;
  }

  > svg {
    position: absolute;
    left: 1rem;
    top: 50%;
    transform: translateY(-50%);
  }

  > div {
    position: absolute;
    right: 0.5rem;
    top: 50%;
    transform: translateY(-50%);
  }
`;

const SearchInput = styled.input<Omit<React.CSSProperties, 'translate'>>`
  width: 100%;
  height: 100%;
  padding-right: ${({ paddingRight }) => paddingRight && `${paddingRight}`};
  background-color: transparent;
  border: 0;
  outline: none;
  font-size: 1rem;
  font-weight: 700;
  text-align: ${({ textAlign }) => textAlign && `${textAlign}`};
  color: ${COLOR.WHITE_100};

  ::placeholder {
    font-size: 1rem;
    line-height: 1.5;
    color: ${COLOR.GRAY_200};
  }
  // input의 type이 search일 때 기본으로 제공되는 reset 버튼을 삭제하는 코드
  ::-ms-clear,
  ::-ms-reveal {
    display: none;
    width: 0;
    height: 0;
  }
  ::-webkit-search-decoration,
  ::-webkit-search-cancel-button,
  ::-webkit-search-results-button,
  ::-webkit-search-results-decoration {
    display: none;
  }
`;

const ResetButton = styled.button`
  ${Flex({ justifyContent: 'center', alignItems: 'center' })};
  height: 100%;
  background-color: transparent;
  border: 0;
  position: absolute;
  right: 2.5rem;

  span {
    background-color: ${COLOR.GRAY_200};
    color: ${COLOR.WHITE_200};
    border-radius: 50%;
    width: 1.125rem;
    height: 1.125rem;
    font-size: 0.7rem;
    font-weight: 900;
    line-height: 1.7;
    vertical-align: middle;
  }
`;

const SearchButton = styled.button`
  ${Flex({ flexDirection: 'column', justifyContent: 'center', alignItems: 'center' })}

  width: 2rem;
  height: 2rem;
  border: 0;
  background-color: transparent;
  position: absolute;
  right: 0.5rem;
  top: 50%;
  transform: translateY(-50%);
`;

export { Container, SearchInput, ResetButton, SearchButton };
