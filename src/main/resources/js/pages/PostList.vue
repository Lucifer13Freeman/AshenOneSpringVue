<template>
    <v-container>
        <v-layout align-space-around justify-start column>
            <post-form v-if="visible" :postAttr="post" />
            <post-item v-for="(post, index) in posts"
                        :key="`post.id-${index}`"
                        :post="post"
                        :editPost="editPost" />
            <lazy-loader :isNews="isNews" :userId="profileId"></lazy-loader>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters, mapState } from 'vuex';
    import PostItem from 'components/posts/PostItem.vue';
    import PostForm from 'components/posts/PostForm.vue';
    import LazyLoader from 'components/LazyLoader.vue';

    export default {
        name: 'PostList',
        props: ['isNews', "userId"],
        components: {
            PostItem,
            PostForm,
            LazyLoader
        },
        data() {
            return {
                post: null
            }
        },
        methods: {
            editPost(post) { this.post = post; }
        },
        computed: {
            ...mapState(['profile']),
            ...mapGetters([
                'sortedPosts', 
                'sortedNewsPosts', 
                'sortedUserPosts'
            ]),
            profileId() { return this.userId || this.$route.params.id; },
            posts() 
            {
                return this.profileId 
                    ? this.sortedUserPosts 
                    : this.isNews 
                    ? this.sortedNewsPosts 
                    : this.sortedPosts;
            },
            visible() { 
                return this.profile && 
                            this.userId 
                                ? this.profileId === this.profile.id 
                                : true;
            }
        }
    }
</script>

<style scoped>
</style>