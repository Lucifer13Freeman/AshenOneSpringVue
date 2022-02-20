<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            <user-link
                :user="post.author"
                size="48"
            ></user-link>
            <div class="pt-3">
                {{ post.text }}
            </div>
        </v-card-text>
        <media v-if="post.link" :post="post"></media>
        <v-card-actions>
            <v-btn value="Edit" 
                @click="edit" 
                small text rounded
                :disabled="!isAuthor"
            >
                Edit
                <v-icon right>mdi-pencil</v-icon>
            </v-btn>
            <v-btn icon @click="del" small :disabled="!isAuthor">
                <v-icon>mdi-delete</v-icon>
            </v-btn>
        </v-card-actions>
        <comment-list
            :comments="post.comments"
            :post-id="post.id"
        ></comment-list>
    </v-card>
</template>

<script>
    import { mapActions, mapState } from 'vuex'; 
    import Media from 'components/media/Media.vue';
    import CommentList from 'components/comments/CommentList.vue';
    import UserLink from 'components/UserLink.vue';

    export default {
        name: 'PostItem',
        props: ['post', 'editPost'],
        components: { Media, CommentList, UserLink },
        computed: {
            ...mapState(['profile']),
            isAuthor() {
                return this.post?.author?.id === this.profile.id;
            }
        },
        methods: {
            ...mapActions(['deletePostAction']),
            edit() { this.editPost(this.post); },
            async del() { await this.deletePostAction(this.post); }
        }
    }
</script>

<style scoped>
</style>