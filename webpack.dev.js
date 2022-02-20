const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const path = require('path');

module.exports = merge(common, {
  mode: 'development',
//   entry: path.join(__dirname, 'src', 'main', 'resources', 'js', 'main.js'),
  devtool: 'inline-source-map',
  devServer: {
    headers: {
      'Access-Control-Allow-Origin': '*'
    },
    // contentBase: './dist',
    static: {
        directory: path.resolve(__dirname, "./dist"),
        staticOptions: {},
        publicPath: "./dist",
        serveIndex: true,
        watch: true,
    },
    compress: true,
    port: 3000,
    allowedHosts: [
      'localhost:8080'
    ],
    client: {
      overlay: {
        errors: true,
      }
    }
  },
  stats: {
     all: false,
     errors: true
  },
//   resolve: {
//     modules: [
//         path.join(__dirname, 'src', 'main', 'resources', 'js'),
//         path.join(__dirname, 'node_modules'),
//     ],
//   }
});