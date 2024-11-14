import path from 'path';
import webpack from 'webpack';
import 'webpack-dev-server';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import dotenv from 'dotenv';

// Load environment variables from .env file
const env = dotenv.config().parsed;
const host = process.env.HOST;
const port = process.env.PORT;

// Create a new Webpack define plugin
const definePlugin = new webpack.DefinePlugin({
  'process.env': JSON.stringify(env)
});

const config: webpack.Configuration = {
  mode: 'production',
  entry: './src/index.tsx',
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js',
  },
  module: {
    rules: [
      { test: /\.tsx?$/, use: ['ts-loader'], exclude: /node_modules/ },
      { test: /\.css$/, use:['style-loader', 'css-loader'], exclude: /node_modules/}
    ],
  },
  devServer: {
    static: {
        directory: path.join(__dirname, 'public'),
    },
    compress: true,
    port: 3000,
    historyApiFallback: true,
    proxy: [{
        context: ['/api'],
        target: `http://${host}:${port}`,
        changeOrigin: true,
        secure: false
    }],
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js'],
  },
  plugins: [definePlugin]
};

export default config;
