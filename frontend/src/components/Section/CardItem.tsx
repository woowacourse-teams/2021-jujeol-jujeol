import Card from '../Card/Card';
import { ItemImage, ItemInfo } from './CardItem.styles';

interface Props {
  imageUrl: string;
  title: string;
  description: string;
  onClick?: () => void;
}

const CardItem = ({ imageUrl, title, description, onClick }: Props) => {
  return (
    <Card width="13rem" height="17rem" onClick={onClick}>
      <ItemImage src={imageUrl} alt={title} />
      <ItemInfo>
        <h3>{title}</h3>
        <p>{description}</p>
      </ItemInfo>
    </Card>
  );
};

export default CardItem;
