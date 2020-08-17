import Home from "./components/Apartment/Home.vue";
import Profile from "./components/User/Profile.vue";
import Registration from "./components/User/Registration.vue";

export const routes = [
  {
    path: "",
    name: "home",
    component: Home,
  },
  {
    path: "/user",
    component: Profile,
  },
  {
    path: "/register",
    component: Registration,
  },
];
