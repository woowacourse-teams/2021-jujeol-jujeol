import { Link } from 'react-router-dom';
import { PATH } from 'src/constants';
import Card from '../Card/Card';
import { ItemList, ItemImage, ItemInfo } from './CardList.styles';

interface Props {
  items: ItemList.Drinks[];
  count?: number;
  onItemClick?: () => void;
}

const CardList = ({ items, count = 7, onItemClick }: Props) => {
  return (
    <ItemList col={`${count + 2}`} colGap="2rem">
      {items?.map(({ id, name, imageUrl, alcoholByVolume }: ItemList.Drinks) => (
        <li key={id} onClick={onItemClick}>
          <Link to={`${PATH.DRINKS}/${id}`}>
            <Card width="13rem" height="17rem">
              <ItemImage src={imageUrl} alt={name} />
              <ItemInfo>
                <h3>{name}</h3>
                <p>{`${alcoholByVolume}%`}</p>
              </ItemInfo>
            </Card>
          </Link>
        </li>
      ))}
    </ItemList>
  );
};

export default CardList;
