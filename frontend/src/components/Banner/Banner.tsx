import { Container } from './Banner.styles';

interface Props {
  type: 'IMAGE';
  title: '프로모션 배너';
  src: string;
  alt: string;
}

const Banner = ({ type, title, src, alt }: Props) => {
  return (
    <Container>
      {type === 'IMAGE' && (
        <>
          <h2>{title}</h2>
          <img src={src} alt={alt} loading="lazy" />
        </>
      )}
    </Container>
  );
};

export default Banner;
