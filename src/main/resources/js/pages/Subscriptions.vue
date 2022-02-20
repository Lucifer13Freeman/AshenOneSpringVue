<template>
    <v-container>
        <v-layout justify-space-around>
            <v-list>
                <v-list-item
                    v-for="(sub, index) in subscriptions"
                    :key="`sub.subscriber.id-${index}`"
                >
                    <user-link
                        :user="sub.subscriber"
                        size="24"
                    ></user-link>
                    <v-btn
                        @click="changeSubscriptionStatus(sub.subscriber.id)"
                    >
                        {{sub.active ? "Dismiss" : "Approve"}}
                    </v-btn>
                </v-list-item>
            </v-list>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile';
    import UserLink from 'components/UserLink.vue';

    export default {
        name: 'Subscriptions',
        components: { UserLink },
        data() {
            return {
                subscriptions: []
            }
        },
        methods: {
            async changeSubscriptionStatus(subscriberId) 
            {
                await profileApi.changeSubscriptionStatus(subscriberId);

                const index = this.subscriptions.findIndex(subs =>
                    subs.subscriber.id === subscriberId
                );

                const subscription = this.subscriptions[index];

                this.subscriptions = [
                    ...this.subscriptions.slice(0, index),
                    {
                        ...subscription,
                        active: !subscription.active
                    },
                    ...this.subscriptions.slice(index + 1)
                ];
            }
        },
        async beforeMount() {
            const { data } = await profileApi.subscriberList(this.$store.state.profile.id)
            this.subscriptions = data;
        }
    }
</script>

<style scoped>
</style>