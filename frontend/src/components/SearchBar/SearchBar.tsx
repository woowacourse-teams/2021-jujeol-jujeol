import { ChangeEvent, FormEvent, InputHTMLAttributes, useContext, useState } from 'react';
import { useHistory } from 'react-router-dom';

import { COLOR, MESSAGE, PATH, SEARCH } from 'src/constants';
import { SearchIcon } from '../@Icons';
import CancelIcon from '../@Icons/CancelIcon';
import GoBackButton from '../@shared/Button/GoBackButton';
import IconButton from '../@shared/Button/IconButton';
import { SnackbarContext } from '../@shared/Snackbar/SnackbarProvider';
import { Container, SearchInput } from './SearchBar.styles';

const SearchBar = ({
  onClick,
  placeholder,
  readOnly,
}: InputHTMLAttributes<HTMLInputElement | HTMLFormElement>) => {
  const history = useHistory();

  const [value, setValue] = useState('');
  const { setSnackbarMessage } = useContext(SnackbarContext) ?? {};

  const onInputWords = (event: ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value);
  };

  const onResetInput = () => setValue('');

  const onSearch = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const searchWords = value.trim();

    if (readOnly) {
      history.push(PATH.SEARCH);
      return;
    }

    if (searchWords === '') {
      setSnackbarMessage?.({ type: 'ERROR', message: MESSAGE.SEARCH_INPUT_CANNOT_EMPTY });
      return;
    }

    history.push(`${PATH.SEARCH_RESULT}?words=${searchWords}`);
  };

  return (
    <Container
      width={readOnly ? '80%' : '90%'}
      padding={readOnly ? '0.5rem 1.5rem' : '0.5rem 0.8rem'}
      onSubmit={onSearch}
      onClick={onClick}
    >
      {readOnly ? (
        <SearchIcon color={COLOR.GRAY_100} width="1.2rem" />
      ) : (
        <GoBackButton color={COLOR.WHITE} />
      )}
      <SearchInput
        title="검색"
        type="search"
        value={value}
        placeholder={placeholder}
        paddingRight={readOnly ? '0' : '3.5rem'}
        textAlign={readOnly ? 'center' : 'left'}
        onChange={onInputWords}
        maxLength={SEARCH.MAX_LENGTH}
        required
        readOnly={readOnly}
        role={readOnly ? 'link' : 'input'}
      />
      {!readOnly && (
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
