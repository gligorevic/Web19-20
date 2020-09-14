import Home from "./components/Apartment/Home.vue";
import Profile from "./components/User/Profile.vue";
import Registration from "./components/User/Registration.vue";
import Login from "./components/User/Login.vue";

import MyApartments from "./components/Apartment/MyApartments";
import AddApartment from "./components/Apartment/AddApartmentPage";

import Users from "./components/User/Users.vue";
import Reservations from "./components/Reservation/Reservations.vue";
import ReservationsGuest from "./components/Reservation/ReservationsGuest.vue";
import ReservationsHost from "./components/Reservation/ReservationsHost.vue";

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

    path: "/addApartment",
    component: AddApartment,
    meta: {
      auth: true,
      roleRequired: ["HOST"],
    },
  },
  {
    path: "/myApartments",
    component: MyApartments,
    meta: {
      auth: true,
      roleRequired: ["HOST"],
    },
  },

  {
    path:"/users",
    component: Users,
    meta: {
      auth: true,
    },
  },
  {
    path:"/reservations",
    component: Reservations,
    meta:{
      auth:true,
    }
  },
  {
    path:"/reservationsguest",
    component: ReservationsGuest,
    meta:{
      auth:true,
    }
  },
  {
    path:"/reservationshost",
    component: ReservationsHost,
    meta:{
      auth:true,
    }
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
