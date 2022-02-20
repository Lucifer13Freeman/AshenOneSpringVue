const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const path = require('path');

module.exports = merge(common, {
  mode: 'production',
//   entry: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'),
  output: {
    filename: 'main.js',
    path: path.resolve(__dirname, 'src', 'main', 'resources', 'static', 'js'),
    clean: true,
  },
});