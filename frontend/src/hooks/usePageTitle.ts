import { useEffect, useState } from 'react';

const usePageTitle = (initialTitle?: string) => {
  const [pageTitle, setPageTitle] = useState(initialTitle);

  useEffect(() => {
    document.title = `주절주절 ${initialTitle ? '| ' + initialTitle : ''}`;
  }, [pageTitle]);

  return { setPageTitle };
};

export default usePageTitle;
