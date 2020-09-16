import Profile from "./components/User/Profile.vue";
import Registration from "./components/User/Registration.vue";
import Login from "./components/User/Login.vue";
import Users from "./components/User/Users.vue";

import Home from "./components/Apartment/Home.vue";
import MyApartments from "./components/Apartment/MyApartments.vue";
import AllApartments from "./components/Apartment/AllApartments.vue";

import AddApartment from "./components/Apartment/AddApartmentPage.vue";
import EditApartmentPage from "./components/Apartment/EditApartmentPage.vue";
import ApartmentView from "./components/Apartment/ApartmentView.vue";

import Reservations from "./components/Reservation/Reservations.vue";
import ReservationsGuest from "./components/Reservation/ReservationsGuest.vue";
import ReservationsHost from "./components/Reservation/ReservationsHost.vue";

import ApartmentViewWraper from "./components/Apartment/Home/ApartmentViewWraper.vue";

import Amenities from "./components/Amenity/Amenities.vue";

// import { eventBus } from "./main";

export const routes = [
  {
    path: "",
    name: "home",
    component: Home,
  },
  {
    path: "/apartment/:id/res",
    component: ApartmentViewWraper,
  },
  {
    path: "/user",
    component: Profile,
    meta: {
      auth: true,
    },
  },
  {
    path: "/apartment/:id",
    component: ApartmentView,
    meta: {
      auth: true,
      roleRequired: ["HOST", "ADMIN"],
    },
  },
  {
    path: "/apartment/:id/edit",
    component: EditApartmentPage,
    meta: {
      auth: true,
      roleRequired: ["HOST", "ADMIN"],
    },
  },
  {
    path: "/allApartments",
    component: AllApartments,
    meta: {
      auth: true,
      roleRequired: ["ADMIN"],
    },
  },
  {
    path: "/amenities",
    component: Amenities,
    meta: {
      auth: true,
      roleRequired: ["ADMIN"],
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
    path: "/users",
    component: Users,
    meta: {
      auth: true,
    },
  },
  {
    path: "/reservations",
    component: Reservations,
    meta: {
      auth: true,
    },
  },
  {
    path: "/reservationsguest",
    component: ReservationsGuest,
    meta: {
      auth: true,
    },
  },
  {
    path: "/reservationshost",
    component: ReservationsHost,
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
