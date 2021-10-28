import { useHistory } from 'react-router-dom';

import { COLOR } from 'src/constants';
import ArrowIcon from '../@Icons/ArrowIcon';
import Heading from '../@shared/Heading/Heading';
import { Header, MoveViewAllPageButton, PreviewSection } from './Preview.styles';

interface Props {
  title: string;
  path: string;
  children: React.ReactNode;
  isShowMoreEnabled?: boolean;
}

const Preview = ({ title, path, children, isShowMoreEnabled }: Props) => {
  const history = useHistory();

  const onMoveToShowMore = () => history.push(path);

  return (
    <PreviewSection>
      <Header>
        <Heading.level3>{title}</Heading.level3>
        {isShowMoreEnabled && (
          <MoveViewAllPageButton fontSize="0.8rem" onClick={onMoveToShowMore}>
            <span>더보기</span>
            <ArrowIcon direction="RIGHT" color={COLOR.WHITE} width="0.5rem" height="0.5rem" />
          </MoveViewAllPageButton>
        )}
      </Header>
      {children}
    </PreviewSection>
  );
};

export default Preview;
