import { InputHTMLAttributes, ChangeEvent, useState, FormEvent } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import { COLOR, PATH, SEARCH } from 'src/constants';
import Arrow from '../Arrow/Arrow';
import { SearchIcon } from '../Icons';
import { Container, ResetButton, SearchButton, SearchInput } from './SearchBar.styles';

const SearchBar = ({
  onClick,
  placeholder,
  readOnly,
}: InputHTMLAttributes<HTMLInputElement | HTMLFormElement>) => {
  const history = useHistory();
  const location = useLocation();

  const [value, setValue] = useState('');

  const onMoveToPrevPage = () => history.goBack();

  const onInputWords = (event: ChangeEvent<HTMLInputElement>) => setValue(event.target.value);
  const onResetInput = () => setValue('');

  const isMainPage = location.pathname === PATH.HOME || location.pathname === PATH.ROOT;

  const onSearch = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (value === '') return;

    history.push(`${PATH.SEARCH_RESULT}?words=${value}`);
  };

  return (
    <Container
      width={isMainPage ? '80%' : '90%'}
      padding={isMainPage ? '0.5rem 1.5rem' : '0.5rem 1.5rem'}
      onSubmit={onSearch}
      onClick={onClick}
    >
      {isMainPage ? (
        <SearchIcon color={COLOR.WHITE_200} width="1.2rem" />
      ) : (
        <button type="button" onClick={onMoveToPrevPage}>
          <Arrow size="0.7rem" borderWidth="2px" dir="LEFT" />
        </button>
      )}
      <SearchInput
        type="search"
        value={value}
        placeholder={placeholder}
        paddingRight={isMainPage ? '0' : '3.5rem'}
        textAlign={isMainPage ? 'center' : 'left'}
        onChange={onInputWords}
        maxLength={SEARCH.MAX_LENGTH}
        required
        autoFocus
        readOnly={readOnly}
      />
      {!isMainPage && (
        <>
          {value && (
            <ResetButton type="reset" onClick={onResetInput}>
              <span>X</span>
            </ResetButton>
          )}
          <SearchButton>
            <SearchIcon color={COLOR.WHITE_200} width="1.4rem" />
          </SearchButton>
        </>
      )}
    </Container>
  );
};

export default SearchBar;
