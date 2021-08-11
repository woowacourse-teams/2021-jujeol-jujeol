import { useHistory } from 'react-router-dom';
import Arrow from '../@shared/Arrow/Arrow';
import { PreviewSection, Header, MoveViewAllPageButton } from './Preview.styles';

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
        <h3>{title}</h3>
        {isShowMoreEnabled && (
          <MoveViewAllPageButton fontSize="0.8rem" onClick={onMoveToShowMore}>
            <span>더보기</span>
            <Arrow dir="RIGHT" size="0.4rem" borderWidth="0.0625rem" />
          </MoveViewAllPageButton>
        )}
      </Header>
      {children}
    </PreviewSection>
  );
};

export default Preview;
