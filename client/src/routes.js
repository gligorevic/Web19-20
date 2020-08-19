import Home from "./components/Apartment/Home.vue";
import Profile from "./components/User/Profile.vue";
import Registration from "./components/User/Registration.vue";
import Login from "./components/User/Login.vue";
// import { eventBus } from "./main";

export const routes = [
  {
    path: "",
    name: "home",
    component: Home,
  },
  {
    path: "/user",
    component: Profile,
    meta: {
      auth: true,
    },
  },
  {
    path: "/register",
    component: Registration,
    meta: {
      loggedOut: true,
    },
  },
  {
    path: "/login",
    component: Login,
    meta: {
      loggedOut: true,
    },
  },
];
