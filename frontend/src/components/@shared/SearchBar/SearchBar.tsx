import { InputHTMLAttributes, ChangeEvent, useState, FormEvent } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import { COLOR, PATH, SEARCH } from 'src/constants';
import IconButton from '../Button/IconButton';
import GoBackButton from '../Button/GoBackButton';

import { SearchIcon } from '../../@Icons';
import CancelIcon from '../../@Icons/CancelIcon';
import { Container, SearchInput } from './SearchBar.styles';

const SearchBar = ({
  onClick,
  placeholder,
  readOnly,
}: InputHTMLAttributes<HTMLInputElement | HTMLFormElement>) => {
  const history = useHistory();
  const location = useLocation();

  const [value, setValue] = useState('');

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
      padding={isMainPage ? '0.5rem 1.5rem' : '0.5rem 0.8rem'}
      onSubmit={onSearch}
      onClick={onClick}
    >
      {isMainPage ? (
        <SearchIcon color={COLOR.GRAY_100} width="1.2rem" />
      ) : (
        <GoBackButton color={COLOR.WHITE} />
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
          <IconButton type="reset" size="X_SMALL" onClick={onResetInput} hidden={!value}>
            <CancelIcon color={COLOR.WHITE} />
          </IconButton>
          <IconButton size="X_SMALL">
            <SearchIcon color={COLOR.GRAY_100} />
          </IconButton>
        </>
      )}
    </Container>
  );
};

export default SearchBar;
