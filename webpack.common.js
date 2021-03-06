const path = require('path');
// const { VueLoaderPlugin } = require('vue-loader');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
  devtool: 'source-map',
  entry: path.join(__dirname, 'src', 'main', 'resources', 'js', 'main.js'),
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /(node_modules|bower_components)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: [
              [
                '@babel/preset-env',
                {
                  targets: {
                    esmodules: true,
                  },
                },
              ],
            ],
          }
        }
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
        test: /\.css$/,
        use: [
          'vue-style-loader',
          'css-loader'
        ]
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin()
  ],
  resolve: {
      modules: [
          path.join(__dirname, 'src', 'main', 'resources', 'js'),
          path.join(__dirname, 'node_modules'),
      ],
  }
}