import Vue from 'vue';

export default {
    create: async post => await Vue.axios.post(`/post/`, post),
    update: async post => await Vue.axios.put(`/post/${post.id}`, post),
    delete: async post => await Vue.axios.delete(`/post/${post.id}`),
    page: async page => await Vue.axios.get('/post/', { params: { page } }),
    newsPage: async page => await Vue.axios.get('/post/news/', { params: { page } }),
    userPage: async (page, profileId) => await Vue.axios.get(`/post/profile/${profileId}`, { params: { page } })
}