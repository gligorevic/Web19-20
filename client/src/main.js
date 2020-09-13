import Vue from "vue";
import VueRouter from "vue-router";
import App from "./App.vue";
import Axios from "axios";
import jwtDecode from "jwt-decode";

let jwt = localStorage.getItem("jwt");
if (jwt) {
  var decodedToken = jwtDecode(jwt);
  console.log(decodedToken);
  if (Date.parse(new Date()) / 1000 >= decodedToken.exp) {
    localStorage.clear();
    Axios.defaults.headers["Authorization"] = "";
    jwt = null;
    decodedToken = {};
  } else {
    Axios.defaults.headers["Authorization"] = "Bearer " + jwt;
  }
}

export const eventBus = new Vue({
  data: {
    isUserLoggedIn: !!jwt,
    currentUser: { ...decodedToken },
  },
  methods: {
    setUserLoggedIn: function(loggedIn) {
      this.isUserLoggedIn = loggedIn;
      if (!loggedIn) {
        this.currentUser = {};
        localStorage.clear();
        Axios.defaults.headers["Authorization"] = "";
      } else {
        Axios.defaults.headers["Authorization"] =
          "Bearer " + localStorage.getItem("jwt");
        this.currentUser = jwtDecode(localStorage.getItem("jwt"));
      }
      this.$emit("loggedInChanged");
    },
    showMessage: function(msgObject) {
      this.$emit("showMessage", msgObject);
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

router.beforeEach((to, from, next) => {
  if (to.meta.auth && !eventBus.isUserLoggedIn) {
    next("/login");
  } else if (
    to.meta.roleRequired &&
    !to.meta.roleRequired.includes(eventBus.currentUser.role)
  ) {
    next("/");
  } else if (to.meta.loggedOut && eventBus.isUserLoggedIn) {
    next("/");
  } else {
    next();
  }
});

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
