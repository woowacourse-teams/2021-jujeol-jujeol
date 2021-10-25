import React from 'react';
import { Link } from 'react-router-dom';

import Heading from '../@shared/Heading/Heading';
import { Title } from './Section.styles';

interface Props {
  title: string;
  titleAlign?: 'left' | 'center' | 'right';
  subTitle?: string;
  isShowMoreEnabled?: boolean;
  showMoreLink?: string;
  children: React.ReactNode;
}

const Section = ({
  title,
  titleAlign = 'left',
  subTitle = '',
  isShowMoreEnabled = true,
  showMoreLink,
  children,
}: Props) => {
  return (
    <section>
      <Title textAlign={titleAlign} isShowMoreEnabled={isShowMoreEnabled}>
        <div>
          <Heading.level2>{title}</Heading.level2>
          {subTitle && <p>{subTitle}</p>}
        </div>
        {isShowMoreEnabled && showMoreLink && <Link to={showMoreLink}>{'더보기 >'}</Link>}
      </Title>
      {children}
    </section>
  );
};

export default Section;
