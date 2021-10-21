import { css } from '@emotion/react';
import { PreferenceRate } from 'src/pages/HomePage/styles';
import LineClamp from 'src/styles/LineClamp';
import { StarIcon } from '../@Icons';
import Heading from '../@shared/Heading/Heading';
import { Item, ItemInfo } from './ListItem.styles';

interface Props {
  imageUrl: string;
  title: string;
  description: string;
  preferenceType: 'MY' | 'AVG' | 'EXPECTED';
  preferenceRate: number;
  onClick?: () => void;
}

const preferenceKR = {
  MY: '내 선호도',
  AVG: '평균 선호도',
  EXPECTED: '예상 선호도',
};

const ListItem = ({
  imageUrl,
  title,
  description,
  preferenceType,
  preferenceRate,
  onClick,
}: Props) => {
  return (
    <Item onClick={onClick}>
      <img src={imageUrl} alt={title} loading="lazy" />
      <ItemInfo>
        <Heading.level3
          css={css`
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
            {preferenceRate.toFixed(1)}
          </PreferenceRate>
        )}
      </ItemInfo>
    </Item>
  );
};

export default ListItem;
