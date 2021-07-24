import React from 'react';
import PersonalReviewCard from 'src/components/Card/PersonalReviewCard';
import { Item } from './MyReviewItem.styles';

const MyReviewItem = () => {
  return (
    <Item>
      <PersonalReviewCard />
    </Item>
  );
};

export default MyReviewItem;
