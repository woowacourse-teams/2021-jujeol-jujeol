/** @type {import("snowpack").SnowpackUserConfig } */
export default {
  mount: {
    public: '/',
    src: '/dist',
  },
  plugins: ['@snowpack/plugin-typescript', '@snowpack/plugin-babel', '@snowpack/plugin-dotenv'],
  routes: [{ match: 'routes', src: '.*', dest: '/index.html' }],
  optimize: {
    bundle: true,
    minify: true,
    splitting: true,
    treeshake: true,
  },
  packageOptions: {
    polyfillNode: true,
  },
  devOptions: {
    port: 3000,
  },
  buildOptions: {},
  alias: {
    src: './src',
    public: './public',
  },
  extends: './tsconfig.json',
  exclude: ['**/test.tsx', '**/*.test.tsx'],
};
