import Vue from 'vue';

export default {
    get: async id => await Vue.axios.get(`/profile/${id}`),
    changeSubscription: async profileId => await Vue.axios.post(`/profile/subscription/${profileId}`),
    subscriberList: async profileId => await Vue.axios.get(`/profile/subscribers/${profileId}`),
    changeSubscriptionStatus: async subscriberId => await Vue.axios.post(`/profile/subscription-status/${subscriberId}`)
}