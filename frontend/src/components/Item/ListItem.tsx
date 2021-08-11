import { Item, ItemInfo } from './ListItem.styles';

interface Props {
  imageUrl: string;
  title: string;
  description: string;
  onClick?: () => void;
}

const ListItem = ({ imageUrl, title, description, onClick }: Props) => {
  return (
    <Item onClick={onClick}>
      <img src={imageUrl} alt={title} loading="lazy" />
      <ItemInfo>
        <h3>{title}</h3>
        <p>{description}</p>
      </ItemInfo>
    </Item>
  );
};

export default ListItem;
