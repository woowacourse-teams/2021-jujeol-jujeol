import { FormEvent } from 'react';
import SearchBar from '../@shared/SearchBar/SearchBar';
import Header from './Header';
import { Logo } from './MainHeader.styles';

const MainHeader = () => {
  const onSearch = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
  };

  return (
    <Header>
      <Logo>주절주절</Logo>
      <SearchBar placeholder="검색어를 입력해주세요" onSubmit={onSearch} />
    </Header>
  );
};

export default MainHeader;
