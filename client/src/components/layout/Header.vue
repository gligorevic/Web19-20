<template>
  <nav>
    <div class="nav-wrapper">
      <a href="#" class="brand-logo" style="display: flex">
        <i class="material-icons">card_travel</i>
        <span>Apartments</span>
      </a>
      <ul id="nav-mobile" class="right hide-on-med-and-down">
        <router-link to="/" tag="li" active-class="active" exact>
          <a>Home</a>
        </router-link>

        <router-link to="/user" tag="li" active-class="active" exact v-if="isUserLoggedIn">
          <a>Profile</a>
        </router-link>

        <li v-if="isUserLoggedIn">
          <a href="#" @click="logout">Logout</a>
        </li>
        <template v-else>
          <router-link to="/login" tag="li" active-class="active" exact>
            <a>Login</a>
          </router-link>
          <router-link to="/register" tag="li" active-class="active" exact>
            <a>Register</a>
          </router-link>
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
    });
  },
};
</script>
