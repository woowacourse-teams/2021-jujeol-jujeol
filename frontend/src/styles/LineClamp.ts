import { css } from '@emotion/react';

const LineClamp = ({ lineClamp }: React.CSSProperties) => css`
  display: -webkit-box;
  -webkit-line-clamp: ${lineClamp ?? 2};
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export default LineClamp;
