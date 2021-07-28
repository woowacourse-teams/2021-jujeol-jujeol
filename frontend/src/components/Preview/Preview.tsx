import { useHistory } from 'react-router-dom';
import ArrowButton from '../@shared/ArrowButton/ArrowButton';
import { PreviewSection, Header } from './Preview.styles';

interface Props {
  title: string;
  path: string;
  children: React.ReactNode;
}

const Preview = ({ title, path, children }: Props) => {
  const history = useHistory();

  return (
    <PreviewSection>
      <Header>
        <h3>{title}</h3>
        <ArrowButton
          fontSize=" 0.8rem"
          dir="RIGHT"
          size="0.4rem"
          borderWidth="0.0625rem"
          onClick={() => history.push(path)}
        >
          <span>더보기</span>
        </ArrowButton>
      </Header>
      {children}
    </PreviewSection>
  );
};

export default Preview;
