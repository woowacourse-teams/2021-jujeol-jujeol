import { InputHTMLAttributes } from 'react';
import { Container } from './SearchBar.styles';

const SearchBar = ({
  onSubmit,
  placeholder,
}: InputHTMLAttributes<HTMLInputElement | HTMLFormElement>) => {
  return (
    <Container onSubmit={onSubmit}>
      <input type="search" placeholder={placeholder} />
      <button>검색</button>
    </Container>
  );
};

export default SearchBar;
