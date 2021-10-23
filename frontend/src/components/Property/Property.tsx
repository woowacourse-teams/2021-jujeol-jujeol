import { Container, Content, TextWrapper, Title } from './Property.styles';

interface Props {
  Icon: ({ color }: { color: string }) => React.ReactElement;
  title: string;
  content: string;
}

const Property = ({ Icon, title, content }: Props) => {
  return (
    <Container>
      <Icon color="white" />
      <TextWrapper>
        <Title>{title}</Title>
        <Content>{content}</Content>
      </TextWrapper>
    </Container>
  );
};

export default Property;
