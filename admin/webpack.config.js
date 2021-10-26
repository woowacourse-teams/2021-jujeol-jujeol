const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const Dotenv = require('dotenv-webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const MiniCSSExtractPlugin = require('mini-css-extract-plugin');

module.exports = {
  entry: {
    admin: './src/js/admin.js',
    login: './src/js/login.js',
  },
  resolve: { extensions: ['.js', '.jsx'] },
  output: {
    filename: '[name]/dist/[name].bundle.js',
    path: path.join(__dirname, '/dist'),
    clean: true,
    assetModuleFilename: 'static/[hash][ext][query]',
  },
  devServer: {
    hot: true,
    open: true,
    historyApiFallback: true,
  },
  devtool: 'source-map',
  plugins: [
    new HtmlWebpackPlugin({
      minify: {
        collapseWhitespace: true,
        removeComments: true,
      },
      filename: 'index.html',
      template: './src/index.html',
      hash: true,
      chunks: ['admin'],
    }),
    new HtmlWebpackPlugin({
      minify: {
        collapseWhitespace: true,
        removeComments: true,
      },
      filename: 'login.html',
      template: './src/pages/login.html',
      hash: true,
      chunks: ['login'],
    }),
    new CopyWebpackPlugin({
      patterns: [
        { from: './src/css', to: './css' },
        { from: './src/bootstrap', to: './bootstrap' },
      ],
    }),
    new Dotenv(),
    new MiniCSSExtractPlugin({
      filename: '[name].css',
    }),
  ],
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/i,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
        },
      },
      {
        test: /\.css$/i,
        use: [MiniCSSExtractPlugin.loader, 'css-loader'],
      },
      {
        test: /\.(eot|svg|ttf|woff|woff2|png|jpg|gif|webp)$/i,
        type: 'asset/resource',
      },
    ],
  },
};
