import Home from "./components/Apartment/Home.vue";
import Profile from "./components/User/Profile.vue";
import Registration from "./components/User/Registration.vue";
import Login from "./components/User/Login.vue";
import { eventBus } from "./main";

export const routes = [
  {
    path: "",
    name: "home",
    component: Home,
  },
  {
    path: "/user",
    component: Profile,
    beforeEnter: (to, from, next) => {
      if (!eventBus.isUserLoggedIn) {
        next("/");
      }
      next();
    },
  },
  {
    path: "/register",
    component: Registration,
    beforeEnter: (to, from, next) => {
      if (eventBus.isUserLoggedIn) {
        next("/");
      }
      next();
    },
  },
  {
    path: "/login",
    component: Login,
    beforeEnter: (to, from, next) => {
      if (eventBus.isUserLoggedIn) {
        next("/");
      }
      next();
    },
  },
];
