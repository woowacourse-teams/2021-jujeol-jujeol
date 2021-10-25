import { ItemList } from './CardList.styles';

interface Props {
  count?: number;
  colGap?: string;
  children: React.ReactNode;
  title?: string;
}

const CardList = ({ colGap = '2rem', count = 7, children, title }: Props) => {
  return (
    <ItemList col={count + 2} colGap={colGap} title={title}>
      {children}
    </ItemList>
  );
};

export default CardList;
