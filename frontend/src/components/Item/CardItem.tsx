import { COLOR } from 'src/constants';
import Card from '../@shared/Card/Card';
import { ItemImage, ItemInfo } from './CardItem.styles';

interface Props {
  imageUrl: string;
  title: string;
  description: string;
  onClick?: () => void;
}

const CardItem = ({ imageUrl, title, description, onClick }: Props) => {
  return (
    <Card width="13rem" height="17rem" onClick={onClick} color={COLOR.WHITE_100}>
      <ItemImage src={imageUrl} alt={title} loading="lazy" />
      <ItemInfo>
        <h3>{title}</h3>
        <p>{description}</p>
      </ItemInfo>
    </Card>
  );
};

export default CardItem;
