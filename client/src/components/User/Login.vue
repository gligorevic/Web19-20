<template>
  <div class="container center">
    <div class="row card formCard">
      <h2 class="addMargin">Log in</h2>
      <form class="col s12">
        <div class="row">
          <div class="input-field col s12">
            <input
              id="email"
              type="email"
              class="validate"
              :class="{invalid: JSON.stringify(user) === JSON.stringify(triedUser)}"
              v-model="user.username"
            />
            <label for="email">Username</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <input
              id="password"
              type="password"
              class="validate"
              :class="{invalid: JSON.stringify(user) === JSON.stringify(triedUser)}"
              v-model="user.password"
            />
            <label for="password">Password</label>
          </div>
          <span
            class="helper-text"
            data-error="wrong"
            data-success="right"
            v-if="JSON.stringify(user) === JSON.stringify(triedUser)"
          >Bad credentials!</span>
        </div>

        <div class="row">
          <div class="col s12">
            <button class="btn btn-primary" @click.prevent="submit">Submit!</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { eventBus } from "../../main";

export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      triedUser: {},
    };
  },
  methods: {
    async submit() {
      try {
        const res = await Axios.post("/api/user/login", {
          ...this.user,
        });
        localStorage.setItem("jwt", res.data);
        eventBus.setUserLoggedIn(true);
        this.$router.push("/");
      } catch (error) {
        eventBus.showMessage({
          message: "Bad credentials! Check your username or password.",
          type: "error",
        });
        this.triedUser = { ...this.user };
      }
    },
  },
};
</script>

<style scoped>
.center {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.addMargin {
  margin-top: 60px;
  margin-bottom: 40px;
}

.formCard {
  min-width: 46%;
  padding: 0 60px;
  margin-top: 30px;
}
</style>