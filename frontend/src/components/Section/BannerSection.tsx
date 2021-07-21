import { Container } from './BannerSection.styles';

interface Props {
  type: 'IMAGE';
  title: '프로모션 배너';
  src: string;
  alt: string;
}

const BannerSection = ({ type, title, src, alt }: Props) => {
  return (
    <Container>
      {type === 'IMAGE' && (
        <>
          <h2>{title}</h2>
          <img src={src} alt={alt} />
        </>
      )}
    </Container>
  );
};

export default BannerSection;
