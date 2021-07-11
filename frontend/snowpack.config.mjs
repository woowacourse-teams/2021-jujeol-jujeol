/** @type {import("snowpack").SnowpackUserConfig } */
export default {
  mount: {
    public: '/',
    src: '/dist',
  },
  plugins: [
    '@snowpack/plugin-webpack',
    '@snowpack/plugin-typescript',
    '@snowpack/plugin-babel',
    '@snowpack/plugin-dotenv',
  ],
  routes: [{ match: 'routes', src: '.*', dest: '/index.html' }],
  optimize: {
    bundle: true,
  },
  packageOptions: {},
  devOptions: {
    port: 3000,
  },
  buildOptions: {},
  alias: {
    src: './src',
    public: './public',
  },
};
