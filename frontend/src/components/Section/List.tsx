import { Link } from 'react-router-dom';
import { PATH } from 'src/constants';
import Grid from '../@shared/Grid/Grid';
import { Item, ItemInfo } from './List.styles';

interface Props {
  items: ItemList.Drinks[];
  count?: number;
  onItemClick?: () => void;
}

const List = ({ items, count, onItemClick }: Props) => {
  return (
    <Grid row={`${count ?? items?.length}`} rowGap="1rem" padding="0 2rem">
      {items?.slice(0, count).map(({ id, name, imageUrl, alcoholByVolume }: ItemList.Drinks) => (
        <Item key={id} onClick={onItemClick}>
          <Link to={`${PATH.DRINKS}/${id}`}>
            <img src={imageUrl} alt={name} />
            <ItemInfo>
              <h3>{name}</h3>
              <p>{`도수 : ${alcoholByVolume}%`}</p>
            </ItemInfo>
          </Link>
        </Item>
      ))}
    </Grid>
  );
};

export default List;
