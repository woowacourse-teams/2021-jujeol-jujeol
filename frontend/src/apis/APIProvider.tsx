import { ReactNode } from 'react';
import { QueryClient, QueryClientProvider } from 'react-query';

interface Props {
  children: ReactNode;
}

const queryClient = new QueryClient();

const APIProvider = ({ children }: Props) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

export default APIProvider;
