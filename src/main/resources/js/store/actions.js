import postsApi from 'api/posts';
import commentsApi from 'api/comments';

export default {
    async createPostAction({ commit, state }, post) 
    {
        if (!post.text) return;
        
        const { data } = await postsApi.create(post);
        // commit('createPostMutation', data);

        const index = state.posts.findIndex(post => post.id === data.id);

        if (index > -1) commit('updatePostMutation', data);
        else commit('createPostMutation', data);
    },
    async updatePostAction({ commit }, post) 
    {
        const { data } = await postsApi.update(post);
        commit('updatePostMutation', data);
    },
    async deletePostAction({ commit }, post) 
    {
        const { status } = await postsApi.delete(post);
        if (status === 200) commit('deletePostMutation', post);
    },
    async createCommentAction({ commit }, comment) 
    {
        const { data } = await commentsApi.create(comment);
        commit('createCommentMutation', data);
    },
    async loadPageAction({commit, state}) 
    {
        const { data } = await postsApi.page(state.currentPage + 1);

        commit('createPostPageMutation', data.posts);
        commit('updateTotalPagesMutation', data.totalPages);
        commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1));
    },
    async loadNewsPageAction({commit, state}) 
    {
        const { data } = await postsApi.newsPage(state.currentNewsPage + 1);

        commit('createNewsPostPageMutation', data.posts);
        commit('updateTotalNewsPagesMutation', data.totalPages);
        commit('updateCurrentNewsPageMutation', Math.min(data.currentPage, data.totalPages - 1));
    },
    async loadUserPageAction({commit, state}, userId) 
    {
        const { data } = await postsApi.userPage(state.currentUserPage + 1, userId);

        commit('createUserPostPageMutation', data.posts);
        commit('updateTotalUserPagesMutation', data.totalPages);
        commit('updateCurrentUserPageMutation', Math.min(data.currentPage, data.totalPages - 1));
    }
}