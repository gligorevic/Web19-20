import Home from "./components/Apartment/Home.vue";
import Profile from "./components/User/Profile.vue";

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
];
