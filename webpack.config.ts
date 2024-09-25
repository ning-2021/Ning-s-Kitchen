import path from 'path';
import webpack from 'webpack';
import 'webpack-dev-server';
import HtmlWebpackPlugin from 'html-webpack-plugin';

const config: webpack.Configuration = {
  mode: 'production',
  entry: './src/index.tsx',
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js',
  },
  module: {
    rules: [
      { test: /\.tsx?$/, use: 'ts-loader', exclude: /node_modules/ },
    ],
  },
  devServer: {
    static: path.join(__dirname, 'dist'),
    compress: true,
    port: 3000,
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js'],
  },
  plugins: [
    new HtmlWebpackPlugin ({
      template: './public/index.html',
    }),
  ],
};

export default config;
