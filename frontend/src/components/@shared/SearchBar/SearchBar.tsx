import { InputHTMLAttributes, ChangeEvent, useEffect, useState } from 'react';
import { useHistory, useLocation } from 'react-router-dom';

import SearchIcon from 'src/components/Icons/search';
import { COLOR, PATH } from 'src/constants';
import Arrow from '../Arrow/Arrow';
import { Container, ResetButton, SearchButton, SearchInput } from './SearchBar.styles';

const SearchBar = ({
  onSubmit,
  onClick,
  placeholder,
}: InputHTMLAttributes<HTMLInputElement | HTMLFormElement>) => {
  const history = useHistory();
  const location = useLocation();

  const [isMainPage, setIsMainPage] = useState(true);
  const [value, setValue] = useState('');

  const onMoveGoBack = () => history.goBack();

  useEffect(() => {
    if (location.pathname === PATH.HOME || location.pathname == PATH.ROOT) {
      setIsMainPage(true);
    } else {
      setIsMainPage(false);
    }
  }, []);

  const onInputWords = (event: ChangeEvent<HTMLInputElement>) => setValue(event.target.value);
  const onResetInput = () => setValue('');

  return (
    <Container
      width={isMainPage ? '80' : '90'}
      padding={isMainPage ? '0.5rem 1.5rem' : '0.5rem 1.5rem'}
      onSubmit={onSubmit}
      onClick={onClick}
    >
      {isMainPage ? (
        <SearchIcon color={COLOR.WHITE_200} width="1.2rem" />
      ) : (
        <button type="button" onClick={onMoveGoBack}>
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
        required
      />
      {!isMainPage && (
        <>
          {value && (
            <ResetButton type="reset" onClick={onResetInput}>
              <span>X</span>
            </ResetButton>
          )}
          <SearchButton type="submit">
            <SearchIcon color={COLOR.GRAY_400} width="1.2rem" />
          </SearchButton>
        </>
      )}
    </Container>
  );
};

export default SearchBar;
