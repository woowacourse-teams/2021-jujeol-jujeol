import { PreviewSection } from './Preview.styles';

interface Props {
  title: string;
  children: React.ReactNode;
}

const Preview = ({ title, children }: Props) => {
  return (
    <PreviewSection>
      <div>
        <h3>{title}</h3>
        <button>더보기</button>
      </div>
      {children}
    </PreviewSection>
  );
};

export default Preview;
