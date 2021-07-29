import { ArrowStyle, ArrowShape } from './Arrow.styles';

const Arrow = ({ size, borderWidth, dir }: ArrowStyle) => {
  return <ArrowShape size={size} borderWidth={borderWidth} dir={dir} />;
};

export default Arrow;
