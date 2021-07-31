import { RefObject, useEffect } from 'react';

const useInfinityScroll = ({
  target,
  fetchNextPage,
  hasNextPage,
}: {
  target: RefObject<HTMLDivElement>;
  fetchNextPage: () => void;
  hasNextPage: boolean | undefined;
}) => {
  useEffect(() => {
    const targetElement = target.current;

    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && hasNextPage) {
          fetchNextPage();
        }
      });
    });

    if (targetElement) {
      observer.observe(targetElement);
    }

    return () => {
      if (targetElement) {
        observer.unobserve(targetElement);
      }
    };
  }, [target.current]);
};

export default useInfinityScroll;
