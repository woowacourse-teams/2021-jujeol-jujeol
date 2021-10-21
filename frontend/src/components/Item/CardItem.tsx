import { COLOR } from 'src/constants';
import { PreferenceRate } from 'src/pages/HomePage/styles';
import Card from '../@shared/Card/Card';
import { StarIcon } from '../@Icons';
import { ItemImage, ItemInfo } from './CardItem.styles';
import Heading from '../@shared/Heading/Heading';
import { css } from '@emotion/react';
import LineClamp from 'src/styles/LineClamp';

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
          <Heading.level3
            css={css`
              font-size: 1.25rem;
              margin-bottom: 0.3rem;
              ${LineClamp({ lineClamp: 2 })};
            `}
          >
            {title}
          </Heading.level3>
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
