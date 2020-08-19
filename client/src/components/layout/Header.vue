<template>
  <nav>
    <div class="nav-wrapper">
      <a href="#" class="brand-logo" style="display: flex">
        <i class="material-icons">card_travel</i>
        <span>Apartments</span>
      </a>
      <ul id="nav-mobile" class="right hide-on-med-and-down">
        <router-link to="/" tag="li" active-class="active" exact>Home</router-link>

        <router-link to="/user" tag="li" active-class="active" exact v-if="isUserLoggedIn">Profile</router-link>

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

<style scoped>
#nav-mobile > li {
  padding: 0px 15px;
  cursor: pointer;
}
</style>