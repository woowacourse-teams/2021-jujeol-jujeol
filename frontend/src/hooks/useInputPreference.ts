import { RefObject, useState } from 'react';

const useNoticeToInputPreference = ({ targetRef }: { targetRef: RefObject<HTMLDivElement> }) => {
  const [isBlinked, setIsBlinked] = useState(false);

  const scrollToPreferenceSection = () => {
    targetRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (!entry.isIntersecting) {
          scrollToPreferenceSection();
        } else {
          observer.disconnect();
        }

        setIsBlinked(true);
      });
    },
    { threshold: 1.0 }
  );

  const observePreferenceSection = () => {
    if (targetRef.current) {
      observer.observe(targetRef.current);
    }

    setTimeout(() => {
      setIsBlinked(false);
    }, 3000);
  };

  return { isBlinked, setIsBlinked, observePreferenceSection };
};

export default useNoticeToInputPreference;
