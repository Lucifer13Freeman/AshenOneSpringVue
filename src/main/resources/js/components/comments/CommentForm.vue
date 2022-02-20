<template>
    <v-layout row class="px-6">
        <v-text-field
            label="New comment"
            placeholder="Write something..."
            v-model="text"
            @keyup.enter="save"
        />
        <v-btn @click="save">
            Create
        </v-btn>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex';

    export default {
        name: 'CommentForm',
        props: ['postId'],
        data() {
            return {
                text: ''
            }
        },
        methods: {
            ...mapActions(['createCommentAction']),
            save() 
            {
                this.createCommentAction(
                {
                    text: this.text,
                    post: {
                        id: this.postId
                    }
                });
                
                this.text = '';
            }
        }
    }
</script>

<style scoped>
</style>