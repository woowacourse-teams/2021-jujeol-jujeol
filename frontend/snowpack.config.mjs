/** @type {import("snowpack").SnowpackUserConfig } */
export default {
  mount: {
    public: '/',
    src: '/dist',
  },
  plugins: ['@snowpack/plugin-webpack', '@snowpack/plugin-typescript', '@snowpack/plugin-babel'],
  routes: [{ match: 'routes', src: '.*', dest: '/index.html' }],
  optimize: {
    bundle: true,
  },
  packageOptions: {},
  devOptions: {},
  buildOptions: {},
};
