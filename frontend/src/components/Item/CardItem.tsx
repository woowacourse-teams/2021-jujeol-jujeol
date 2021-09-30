import { COLOR } from 'src/constants';
import { PreferenceRate } from 'src/pages/HomePage/styles';
import Card from '../@shared/Card/Card';
import { StarIcon } from '../@shared/Icons';
import { ItemImage, ItemInfo } from './CardItem.styles';

interface Props {
  imageUrl: string;
  title: string;
  description: string;
  preferenceType?: 'MY' | 'AVG' | 'EXPECTED';
  preferenceRate?: number;
  onClick?: () => void;
}

const preferenceKR = {
  MY: '내 선호도',
  AVG: '평균 선호도',
  EXPECTED: '예상 선호도',
};

const CardItem = ({
  imageUrl,
  title,
  description,
  preferenceType,
  preferenceRate,
  onClick,
}: Props) => {
  return (
    <li>
      <Card width="13rem" height="17rem" onClick={onClick} color={COLOR.WHITE}>
        <ItemImage src={imageUrl} alt={title} loading="lazy" />
        <ItemInfo>
          <h3>{title}</h3>
          <p>{description}</p>
          {!!preferenceType && (
            <PreferenceRate type={preferenceType}>
              {preferenceKR[preferenceType]}
              <StarIcon width="12px" />
              {preferenceRate?.toFixed(1)}
            </PreferenceRate>
          )}
        </ItemInfo>
      </Card>
    </li>
  );
};

export default CardItem;
