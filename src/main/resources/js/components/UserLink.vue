<template>
    <router-link
        :to="`/user/${userId}`"
        class="px-3"
    >
        <v-avatar
            v-if="user && user.picture"
            :size="`${size || 36}px`"
        >
            <img
                :src="user.picture"
                :alt="user.name"
            >
        </v-avatar>

        <v-avatar
            v-else
            :size="`${size || 36}px`"
            color="indigo"
        >
            <v-icon dark>mdi-account-circle</v-icon>
        </v-avatar>
        <span>{{ userName }}</span>
    </router-link>
</template>

<script>
    export default {
        name: 'UserLink',
        props: ['user', 'size'],
        computed: {
            userId() {
                return this.user ? String(this.user.id) : 'null';
            },
            userName() {
                return this.user ? this.user.name : 'unknown';
            }
        },
    }
</script>

<style scoped>
span {
    color: black;
    margin-left: 4px;
}
a {
    text-decoration: none;
}
</style>