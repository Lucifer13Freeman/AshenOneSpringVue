// import Vue from 'vue';
// import { createApp } from "vue";
import Vue from 'vue';
import Vuetify from 'vuetify';
import '@babel/polyfill';
import 'api/axios';
import store from 'store/store';
import router from 'router/router';
import App from 'pages/App.vue';
import { connect } from './util/ws';
import 'vuetify/dist/vuetify.min.css';
import * as Sentry from "@sentry/vue";
import { BrowserTracing } from "@sentry/tracing";


Sentry.init({
    Vue,
    dsn: "https://9301763091db4784bcb25d60e9ce0ee6@o1144512.ingest.sentry.io/6208582",
    integrations: [
      new BrowserTracing({
        routingInstrumentation: Sentry.vueRouterInstrumentation(router),
        tracingOrigins: ["localhost", /^\//],
      }),
    ],
    logErrors: true
    // tracesSampleRate: 1.0,
});

Sentry.configureScope(scope => scope.setUser(
{
    id: profile && profile.id,
    username: profile && profile.name,
}));
  
if (profile) connect();

Vue.use(Vuetify);

new Vue({
    vuetify: new Vuetify(),
    el: '#app',
    store,
    router,
    render: a => a(App)
});

// createApp(App)
//     .use(Vuetify)
//     .use(VueAxios, axios)
//     .mount('#app');
