import { ItemList } from './CardList.styles';

interface Props {
  count?: number;
  colGap?: string;
  children: React.ReactNode;
}

const CardList = ({ colGap = '2rem', count = 7, children }: Props) => {
  return (
    <ItemList col={`${count + 2}`} colGap={colGap}>
      {children}
    </ItemList>
  );
};

export default CardList;
