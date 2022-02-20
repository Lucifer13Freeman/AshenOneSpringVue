import Vue from 'vue';

export default {
    create: async comment => await Vue.axios.post(`/comment/`, comment)
}