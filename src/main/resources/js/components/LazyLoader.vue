<template>
    <span></span>
</template>

<script>
    import { mapActions } from 'vuex';

    export default {
        name: 'LazyLoader',
        props: ['isNews', 'userId'],
        methods: mapActions([
            'loadPageAction', 
            'loadNewsPageAction', 
            'loadUserPageAction'
        ]),
        computed: {
            profileId() { return this.$route.params.id || this.userId; },
        },
        mounted() {
            window.onscroll = () => {
                const el = document.documentElement
                const isBottomOfScreen = el.scrollTop + window.innerHeight === el.offsetHeight;
                
                if (isBottomOfScreen) 
                {
                    this.profileId 
                        ? this.loadUserPageAction(this.profileId) 
                        : this.isNews 
                        ? this.loadNewsPageAction() 
                        : this.loadPageAction();
                }
            }
        },
        beforeDestroy() { window.onscroll = null; }
    }
</script>

<style scoped>
</style>