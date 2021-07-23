import { HorizontalList } from './Horizontal.styles';

interface Props {
  children: React.ReactNode;
  count: number;
}

const Horizontal = ({ children, count = 1 }: Props) => {
  return <HorizontalList count={count}>{children}</HorizontalList>;
};

export default Horizontal;
