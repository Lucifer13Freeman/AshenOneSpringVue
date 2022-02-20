<template>
    <v-container>
        <v-layout row>
            <v-text-field
                    label="New Post"
                    placeholder="Write something..."
                    v-model="text"
                    @keyup.enter="save"
            />
            <v-btn @click="save">
                Save
            </v-btn>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapActions } from 'vuex';

    export default {
        name: 'PostForm',
        props: ['postAttr'],
        data() {
            return {
                text: '',
                id: null
            }
        },
        watch: {
            postAttr(newVal, oldVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            ...mapActions(
            [
                'createPostAction',
                'updatePostAction'
            ]),
            async save() 
            {
                const post = { 
                    id: this.id,
                    text: this.text
                };

                if (this.id) await this.updatePostAction(post);
                else await this.createPostAction(post);

                this.text = '';
                this.id = null;
            }
        }
    }
</script>

<style scoped>
</style>