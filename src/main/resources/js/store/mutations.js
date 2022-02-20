
export default {
    createPostMutation(state, post) 
    {
        state.posts = [ ...state.posts, post ];
        state.newsPosts = [ ...state.newsPosts, post ];
        state.userPosts = [ ...state.userPosts, post ];
    },
    setUserPosts(state, posts)
    {
        state.userPosts = [ ...posts ];
        state.currentUserPage = -1;
    },
    updatePostMutation(state, post) 
    {
        const index = state.posts.findIndex(p => p.id === post.id);
        const indexNews = state.newsPosts.findIndex(p => p.id === post.id);
        const indexUser = state.userPosts.findIndex(p => p.id === post.id);
        // if (index > -1) state.posts.splice(index, 1, post);

        if (index > -1) 
        {
            state.posts = [ 
                ...state.posts.slice(0, index),
                post,
                ...state.posts.slice(index + 1)
            ];
        }

        if (indexNews > -1) 
        {
            state.newsPosts = [ 
                ...state.newsPosts.slice(0, indexNews),
                post,
                ...state.newsPosts.slice(indexNews + 1)
            ];
        }

        if (indexUser > -1) 
        {
            state.userPosts = [ 
                ...state.userPosts.slice(0, indexUser),
                post,
                ...state.userPosts.slice(indexUser + 1)
            ];
        }
    },
    deletePostMutation(state, post) 
    {
        const index = state.posts.findIndex(p => p.id === post.id);
        const indexNews = state.newsPosts.findIndex(p => p.id === post.id);
        const indexUser = state.userPosts.findIndex(p => p.id === post.id);
        //   if (index > -1) state.posts.splice(index, 1);

        if (index > -1) 
        {
            state.posts = [ 
                ...state.posts.slice(0, index),
                ...state.posts.slice(index + 1)
            ];
        }

        if (indexNews > -1) 
        {
            state.newsPosts = [ 
                ...state.newsPosts.slice(0, indexNews),
                ...state.newsPosts.slice(indexNews + 1)
            ];
        }

        if (indexUser > -1) 
        {
            state.userPosts = [ 
                ...state.userPosts.slice(0, indexUser),
                ...state.userPosts.slice(indexUser + 1)
            ];
        }
    },
    createCommentMutation(state, comment) 
    {
        const index = state.posts.findIndex(p => p.id === comment.post.id);
        const post = state.posts[index];

        const indexNews = state.newsPosts.findIndex(p => p.id === comment.post.id);
        const newsPost = state.newsPosts[indexNews];

        const indexUser = state.userPosts.findIndex(p => p.id === comment.post.id);
        const userPost = state.userPosts[indexUser];

        if (!post.comments.find(p => p.id === comment.id)) 
        {
            state.posts = [ 
                ...state.posts.slice(0, index),
                {
                    ...post,
                    comments: [
                        ...post.comments,
                        comment
                    ]
                },
                ...state.posts.slice(index + 1)
            ];
        }

        if (!newsPost.comments.find(p => p.id === comment.id)) 
        {
            state.newsPosts = [ 
                ...state.newsPosts.slice(0, indexNews),
                {
                    ...newsPost,
                    comments: [
                        ...newsPost.comments,
                        comment
                    ]
                },
                ...state.newsPosts.slice(indexNews + 1)
            ];
        }

        if (!userPost.comments.find(p => p.id === comment.id)) 
        {
            state.newsPosts = [ 
                ...state.userPosts.slice(0, indexUser),
                {
                    ...userPost,
                    comments: [
                        ...userPost.comments,
                        comment
                    ]
                },
                ...state.userPosts.slice(indexUser + 1)
            ];
        }
    },
    createPostPageMutation(state, posts) 
    {
        const targetPosts = state.posts
            .concat(posts)
            .reduce((res, val) => 
            {
                res[val.id] = val;
                return res;
            }, {});

        state.posts = Object.values(targetPosts);
    },
    createNewsPostPageMutation(state, newsPosts) 
    {
        const targetPosts = state.newsPosts
            .concat(newsPosts)
            .reduce((res, val) => 
            {
                res[val.id] = val;
                return res;
            }, {});

        state.newsPosts = Object.values(targetPosts);
    },
    createUserPostPageMutation(state, userPosts) 
    {
        const targetPosts = state.userPosts
            .concat(userPosts)
            .reduce((res, val) => 
            {
                res[val.id] = val;
                return res;
            }, {});

        state.userPosts = Object.values(targetPosts);
    },
    updateTotalPagesMutation(state, totalPages) 
    {
        state.totalPages = totalPages;
    },
    updateTotalNewsPagesMutation(state, totalNewsPages) 
    {
        state.totalNewsPages = totalNewsPages;
    },
    updateTotalUserPagesMutation(state, totalUserPages) 
    {
        state.totalUserPages = totalUserPages;
    },
    updateCurrentPageMutation(state, currentPage) 
    {
        state.currentPage = currentPage;
    },
    updateCurrentNewsPageMutation(state, currentNewsPage) 
    {
        state.currentNewsPage = currentNewsPage;
    },
    updateCurrentUserPageMutation(state, currentUserPage) 
    {
        state.currentUserPage = currentUserPage;
    }
}