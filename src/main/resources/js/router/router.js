import Vue from 'vue';
import VueRouter from 'vue-router';
import PostList from 'pages/PostList.vue';
import Auth from 'pages/Auth.vue';
import Profile from 'pages/Profile.vue';
import Subscriptions from 'pages/Subscriptions.vue';

Vue.use(VueRouter);

// function removeQueryParams(to) 
// {
//     if (Object.keys(to.query).length)
//         return { path: to.path, query: {}, hash: to.hash }
// }

// function removeHash(to) 
// {
//     if (to.hash) return { path: to.path, query: to.query, hash: '' }
// }

const routes = [
    { path: '/', component: PostList, props: { isNews: false } },
    { path: '/news', component: PostList, props: { isNews: true } },
    { path: '/auth', component: Auth },
    { path: '/user/:id?', component: Profile },
    { path: '/subscriptions/:id', component: Subscriptions },
    { path: '*', component: PostList, props: { isNews: true } }
];

export default new VueRouter(
{
    // mode: 'history',
    routes
});
