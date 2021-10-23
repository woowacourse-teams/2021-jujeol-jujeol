import styled from '@emotion/styled';

import { COLOR } from 'src/constants';

const SurveyLink = styled.a`
  && {
    font-size: 0.9rem;
    display: block;
    margin: 0 auto;
    width: fit-content;
    text-align: center;

    color: ${COLOR.GRAY_200};
    text-decoration: underline;
  }
`;

export { SurveyLink };
