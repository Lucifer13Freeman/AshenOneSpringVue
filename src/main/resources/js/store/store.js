import Vue from 'vue';
import Vuex from 'vuex';
import postsApi from 'api/posts';
import commentsApi from 'api/comments';
import mutations from 'store/mutations';
import actions from 'store/actions';

Vue.use(Vuex);

const store = new Vuex.Store(
{
    state: {
      posts,
      newsPosts,
      userPosts: [],
      profile,
      ...frontendData,
      currentUserPage: -1,
      totalUserPages: 0
    },
    getters: {
      sortedPosts: state => (state.posts || []).sort((a, b) => b.id - a.id),
      sortedNewsPosts: state => (state.newsPosts || []).sort((a, b) => b.id - a.id),
      sortedUserPosts: state => (state.userPosts || []).sort((a, b) => b.id - a.id)
    },
    mutations,
    actions
});

export default store;