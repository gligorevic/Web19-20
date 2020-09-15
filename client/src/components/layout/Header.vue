<template>
  <nav>
    <div class="nav-wrapper blue-grey darken-1">
      <router-link
        to="/"
        tag="li"
        exact
        class="brand-logo"
        style="display: flex; cursor: pointer; font-family: cursive;"
      >
        <i class="material-icons animateIcon" style="margin: 0px 9px;">card_travel</i>
        <span style="text-decoration: underline;">Apartments</span>
      </router-link>
      <ul id="nav-mobile" class="right hide-on-med-and-down">
        <router-link to="/" tag="li" active-class="active" exact>Home</router-link>

        <router-link to="/user" tag="li" active-class="active" exact v-if="isUserLoggedIn">Profile</router-link>
        <router-link
          to="/myApartments"
          tag="li"
          active-class="active"
          exact
          v-if="isUserHost"
        >My apartments</router-link>
        <router-link
          to="/users"
          tag="li"
          active-class="active"
          exact
          v-if="isUserLoggedIn && currentUser.role === 'ADMIN'"
        >Users</router-link>
        <router-link
          to="/reservations"
          tag="li"
          active-class="active"
          exact
          v-if="isUserLoggedIn && currentUser.role === 'ADMIN'"
        >Reservations</router-link>
        <router-link
          to="/reservationshost"
          tag="li"
          active-class="active"
          exact
          v-if="isUserLoggedIn && currentUser.role === 'HOST'"
        >Reservations</router-link>
        <router-link
          to="/reservationsguest"
          tag="li"
          active-class="active"
          exact
          v-if="isUserLoggedIn && currentUser.role === 'GUEST'"
        >Reservations</router-link>
        <li v-if="isUserLoggedIn">
          <span href="#" @click="logout">Logout</span>
        </li>
        <template v-else>
          <router-link to="/login" tag="li" active-class="active" exact>Login</router-link>
          <router-link to="/register" tag="li" active-class="active" exact>Register</router-link>
        </template>
      </ul>
    </div>
  </nav>
</template>

<script>
import { eventBus } from "../../main";
export default {
  data: () => ({
    isUserLoggedIn: eventBus.isUserLoggedIn,
    currentUser: eventBus.currentUser,
  }),
  methods: {
    logout() {
      eventBus.setUserLoggedIn(false);
      this.$router.push("/login");
    },
  },
  created() {
    eventBus.$on("loggedInChanged", () => {
      this.isUserLoggedIn = eventBus.isUserLoggedIn;
      this.currentUser = eventBus.currentUser;
    });
  },
  computed: {
    isUserHost() {
      return eventBus.currentUser.role === "HOST";
    },
  },
};
</script>

<style scoped>
#nav-mobile > li {
  padding: 0px 15px;
  cursor: pointer;
}

.animateIcon {
  display: inline-block;
  animation: kreep 0.75s ease 2s infinite alternate;
}

@keyframes kreep {
  0% {
    transform: scale(1.1, 0.9) rotate(0);
  }
  50% {
    transform: scale(0.9, 1.1) translateY(-0.5rem);
  }
  70% {
    transform: scale(1);
  }
}
</style>