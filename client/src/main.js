import Vue from "vue";
import VueRouter from "vue-router";
import App from "./App.vue";
import Axios from "axios";

const jwt = localStorage.getItem("jwt");

export const eventBus = new Vue({
  data: {
    isUserLoggedIn: !!jwt,
  },
  methods: {
    setUserLoggedIn: function(loggedIn) {
      this.isUserLoggedIn = loggedIn;
      if (!loggedIn) {
        localStorage.clear();
        Axios.defaults.headers["Authorization"] = "";
      } else {
        Axios.defaults.headers["Authorization"] =
          "Bearer " + localStorage.getItem("jwt");
      }
      this.$emit("loggedInChanged");
    },
  },
});

import { routes } from "./routes";

Vue.config.productionTip = false;

Vue.use(VueRouter);
const router = new VueRouter({
  routes,
  mode: "history",
});

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
