import Flex from 'src/styles/Flex';
import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Container = styled.form<Omit<React.CSSProperties, 'translate'>>`
  width: ${({ width }) => width && `${width}`};
  height: 3rem;
  padding: ${({ padding }) => padding && `${padding}`};
  margin: 1.5rem auto;

  ${Flex({ justifyContent: 'space-between', alignItems: 'center' })};

  background-color: rgba(255, 255, 255, 0.3);
  border: 2px solid white;
  border-radius: 2rem;
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
  color: ${COLOR.WHITE};

  -webkit-appearance: none;

  ::placeholder {
    font-size: 1rem;
    color: ${COLOR.GRAY_200};
  }

  ::-ms-clear,
  ::-ms-reveal {
    width: 0;
    height: 0;

    display: none;
  }
  ::-webkit-search-decoration,
  ::-webkit-search-cancel-button,
  ::-webkit-search-results-button,
  ::-webkit-search-results-decoration {
    display: none;
  }
`;

export { Container, SearchInput };
