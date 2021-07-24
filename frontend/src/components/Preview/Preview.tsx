import { useHistory } from 'react-router-dom';
import ArrowButton from '../@shared/ArrowButton/ArrowButton';
import { PreviewSection } from './Preview.styles';

interface Props {
  title: string;
  path: string;
  children: React.ReactNode;
}

const Preview = ({ title, path, children }: Props) => {
  const history = useHistory();

  return (
    <PreviewSection>
      <div>
        <h3>{title}</h3>
        <ArrowButton
          name="더보기"
          fontSize=" 0.8rem"
          dir="RIGHT"
          size="0.4rem"
          borderSize="0.0625rem"
          onClick={() => history.push(path)}
        />
      </div>
      {children}
    </PreviewSection>
  );
};

export default Preview;
