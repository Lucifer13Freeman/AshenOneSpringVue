<template>
    <v-app>
        <v-layout>
            <v-app-bar app>
                <v-app-bar-title>
                    Ashen One
                </v-app-bar-title>
                <v-btn text
                   v-if="profile"
                   :disabled="$route.path === '/'"
                   to="/"
                   class="ml-2"
                >
                    Global
                </v-btn>
                <v-btn text
                   v-if="profile"
                   :disabled="$route.path === '/news'"
                   to="/news"
                   class="ml-1"
                >
                    News
                </v-btn>
                <v-spacer></v-spacer>
                <v-btn text
                    v-if="profile"
                    :disabled="$route.path === '/user'"
                    to="/user"
                >
                    {{profile.name}}
                </v-btn>
                <v-btn v-else 
                    text 
                    href="/login"
                >
                    Sign In
                    <v-icon right>mdi-import</v-icon>
                </v-btn>
                <v-btn v-if="profile" icon href="/logout">
                    <v-icon>mdi-export</v-icon>
                </v-btn>
            </v-app-bar>
            <v-main>
                <router-view></router-view>
            </v-main>
        </v-layout>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex';
    import { addHandler } from 'util/ws';

    export default {
        name: 'App',
        computed: mapState(['profile']),
        methods: {
            ...mapMutations(
            [
                'createPostMutation',
                'updatePostMutation',
                'deletePostMutation',
                'createCommentMutation'
            ])
        },
        created() {
            addHandler(data => 
            {
                if (data.objectType === 'POST') 
                {
                    const postData = data.body;

                    switch (data.eventType) 
                    {
                        case 'CREATE':
                        {
                            this.createPostMutation(postData);
                            break;
                        }
                        case 'UPDATE':
                        {
                            this.updatePostMutation(postData);
                            break;
                        }
                        case 'DELETE':
                        {
                            this.deletePostMutation(postData);
                            break;
                        }
                        default:
                        {
                            console.error(`Looks like the event type if unknown "${data.eventType}"`);
                        }
                    }
                } 
                else if (data.objectType === 'COMMENT') 
                {
                    switch (data.eventType) 
                    {
                        case 'CREATE':
                        {
                            this.createCommentMutation(data.body);
                            break;
                        }
                        default:
                        {
                            console.error(`Looks like the event type if unknown "${data.eventType}"`);
                        }
                    }
                }
                else console.error(`Looks like the object type if unknown "${data.objectType}"`);
            });
        },
        beforeMount() {
            if (!this.profile) this.$router.replace('/auth');
        }
    }
</script>

<style scoped>
</style>