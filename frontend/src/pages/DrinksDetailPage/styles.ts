import styled from '@emotion/styled';
import { COLOR } from 'src/constants';

const Section = styled.section`
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;
  background-color: ${COLOR.PURPLE_900};
  transform: translateY(-6rem);
  padding: 2rem 1.5rem;
  text-align: center;
`;

const PreferenceSection = styled.section`
  margin-bottom: 2rem;

  h3 {
    margin-bottom: 0.5rem;
    font-size: 1rem;
    font-weight: 400;
    color: ${COLOR.WHITE_200};
  }
`;

export { Section, PreferenceSection };
