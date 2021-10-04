import { useEffect, useState, RefObject, MouseEventHandler } from 'react';

const useShowMoreContent = (contentRef: RefObject<HTMLParagraphElement>, content: string) => {
  const [isShowMore, setIsShowMore] = useState(false);
  const [isContentOpen, setIsContentOpen] = useState(false);

  useEffect(() => {
    const contentTarget = contentRef.current;

    if (contentTarget) {
      setIsShowMore(contentTarget.clientHeight < contentTarget.scrollHeight);
    }
  }, [contentRef, content]);

  const onOpenContent = () => {
    setIsContentOpen(true);
  };

  const onCloseContent: MouseEventHandler<HTMLButtonElement> = (event) => {
    event.stopPropagation();

    setIsContentOpen(false);
  };

  return { isShowMore, isContentOpen, onOpenContent, onCloseContent };
};

export default useShowMoreContent;
