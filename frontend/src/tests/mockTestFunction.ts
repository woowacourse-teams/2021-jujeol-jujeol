class MockIntersectionObserver {
  readonly root: Element | null;

  readonly rootMargin: string;

  readonly thresholds: ReadonlyArray<number>;

  constructor() {
    this.root = null;
    this.rootMargin = '';
    this.thresholds = [];
  }

  disconnect() {
    return;
  }

  observe() {
    return;
  }

  takeRecords(): IntersectionObserverEntry[] {
    return [];
  }

  unobserve() {
    return;
  }
}

const mockScrollTo = jest.fn();

const mockMatchMedia = jest.fn().mockImplementation((query) => ({
  matches: false,
  media: query,
  onchange: null,
  addEventListener: jest.fn(),
  removeEventListener: jest.fn(),
  dispatchEvent: jest.fn(),
}));

export { MockIntersectionObserver, mockMatchMedia, mockScrollTo };
