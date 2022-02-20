<template>
    <v-container>
        <v-layout justify-space-around>
            <v-container>
                <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
                    <div class="title mb-3">User profile</div>
                    <v-layout row justify-space-between>
                        <v-flex class="px-1">
                            <v-img :src="profile.picture"></v-img>
                        </v-flex>
                        <v-flex class="px-1">
                            <v-layout column>
                                <v-flex class="title">{{profile.name}}</v-flex>
                                <v-flex>{{profile.locale}}</v-flex>
                                <v-flex>{{profile.gender}}</v-flex>
                                <!-- <v-flex>{{profile.email}}</v-flex> -->
                                <v-flex>{{profile.lastVisit}}</v-flex>
                                <v-flex>
                                    {{profile.subscriptions && profile.subscriptions.length}} Subscriptions
                                </v-flex>
                                <router-link
                                    v-if="isMyProfile"
                                    :to="`/subscriptions/${profile.id}`"
                                >
                                    {{profile.subscribers && profile.subscribers.length}} Subscribers
                                </router-link>
                                <v-flex
                                    v-else
                                >
                                    {{profile.subscribers && profile.subscribers.length}} Subscribers
                                </v-flex>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                    <v-btn
                        v-if="!isMyProfile"
                        @click="changeSubscription"
                        class="mt-6"
                    >
                        {{isISubscribed ? 'Unfollow' : 'Follow'}}
                    </v-btn>
                </v-flex>
            </v-container>
        </v-layout>
        <post-list
            :userId="userId"
        ></post-list>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile';
    import PostList from 'pages/PostList.vue';
    import { mapActions, mapMutations } from 'vuex';

    export default {
        name: 'Profile',
        data() {
            return {
                profile: {}
            }
        },
        components: {
            PostList
        },
        computed: {
            profileId() { return this.$store.state.profile.id; },
            userId() { return this.$route.params.id || this.profileId; },
            isMyProfile() {
                return !this.userId ||
                    this.userId === this.profileId;
            },
            isISubscribed() {
                return this.profile.subscribers &&
                    this.profile.subscribers.find(subscription => 
                    {
                        return subscription.subscriber === this.profileId;
                    })
            }
        },
        watch: {
            '$route'() {
                this.updateProfile()
            }
        },
        methods: {
            ...mapActions(['loadUserPageAction']),
            ...mapMutations(['setUserPosts']),
            async changeSubscription() {
                const { data } = await profileApi.changeSubscription(this.profile.id);
                this.profile = data;
            },
            async updateProfile() {
                const { data } = await profileApi.get(this.userId);
                
                this.profile = data;

                this.setUserPosts([]);
                this.loadUserPageAction(this.profile.id);

                this.$forceUpdate();
            }
        },
        beforeMount() {
            this.updateProfile()
        }
    }
</script>

<style scoped>
    img {
        max-width: 100%;
        height: auto;
    }
</style>