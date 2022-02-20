<template>
    <v-container>
        <v-list two-line class="comments">
            <v-subheader>
                Comments
            </v-subheader>

            <template
                v-for="(comment, index) in comments"
            >
                <v-divider
                    v-if="index > 0"
                    :key="index"
                ></v-divider>

                <comment-item
                    :comment="comment"
                    :key="'comment' + index"
                ></comment-item>
            </template>
        </v-list>
        <comment-form :post-id="postId" class="py-3"></comment-form>
    </v-container>
</template>

<script>
    import CommentForm from 'components/comments/CommentForm.vue';
    import CommentItem from 'components/comments/CommentItem.vue';

    export default {
        name: 'CommentList',
        components: { CommentForm, CommentItem },
        props: ['comments', 'postId']
    }
</script>

<style scoped>
    .comments {
        max-height: 200px;
        overflow-y: auto;
        scrollbar-width: none;
    }
    .comments::-webkit-scrollbar {
        display: none;
    }
</style>