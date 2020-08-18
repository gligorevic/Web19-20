<template>
  <div class="container center">
    <div class="row card formCard">
      <h2 class="addMargin">Registration</h2>
      <form class="col s12">
        <div class="row">
          <div class="input-field col s6">
            <input id="first_name" type="text" class="validate" v-model="user.name" />
            <label for="first_name">First Name</label>
          </div>
          <div class="input-field col s6">
            <input id="last_name" type="text" class="validate" v-model="user.lastName" />
            <label for="last_name">Last Name</label>
          </div>
        </div>

        <div class="row">
          <div class="input-field col s12">
            <input
              id="email"
              type="email"
              :class="{valid: user.username !== triedUsername && user.username.length > 0, invalid: user.username === triedUsername && user.username.length > 0}"
              v-model="user.username"
            />
            <label for="email">Username</label>
            <span
              class="helper-text"
              data-error="wrong"
              data-success="right"
              v-if="user.username === triedUsername && user.username.length > 0"
            >User with {{triedUsername}} already exists.</span>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <input
              id="password"
              type="password"
              :class="{validate: isPasswordCorrect}"
              v-model="user.password"
            />
            <label for="password">Password</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <input
              id="repeatedPassword"
              type="password"
              :class="{validate: isPasswordCorrect}"
              v-model="repeatedPassword"
            />
            <label for="repeatedPassword">Repeat password</label>
            <span
              class="helper-text"
              data-error="wrong"
              data-success="right"
              v-if="showPasswordNotCorrectMessage"
            >Passwords must match.</span>
          </div>
        </div>
        <div class="row">
          <div class="col s3">Gender:</div>
          <div class="col s3">
            <input name="group1" type="radio" id="male" value="MALE" v-model="user.gender" />
            <label for="male">Male</label>
          </div>
          <div class="col s3">
            <input name="group1" type="radio" id="female" value="FEMALE" v-model="user.gender" />
            <label for="female">Female</label>
          </div>
          <div class="col s3">
            <input name="group1" type="radio" id="other" value="OTHER" v-model="user.gender" />
            <label for="other">Other</label>
          </div>
        </div>
        <div class="row">
          <div class="col s12">
            <button
              class="btn btn-primary"
              @click.prevent="submit"
              :disabled="isSomeFieldEmpty"
            >Submit!</button>
          </div>
          <span class="badge" v-if="isSomeFieldEmpty">Fields can't stay empty!</span>
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

        name: "",
        lastName: "",
        gender: "MALE",
      },
      repeatedPassword: "",
      triedUsername: "",
    };
  },
  methods: {
    async submit() {
      console.log(this.user);
      try {
        const res = await Axios.post("/api/user", {
          ...this.user,
        });
        localStorage.setItem("jwt", res.data);
        eventBus.setUserLoggedIn(true);
        eventBus.showMessage({
          message: "You have been successfully registrated!",
          type: "success",
        });
        this.$router.push("/");
      } catch (error) {
        eventBus.showMessage({ message: error?.response?.data, type: "error" });
        this.triedUsername = this.user.username;
      }
    },
  },
  computed: {
    isSomeFieldEmpty() {
      return !this.user.username ||
        !this.user.password ||
        !this.user.name ||
        !this.user.lastName ||
        this.repeatedPassword !== this.user.password
        ? true
        : false;
    },
    isPasswordCorrect() {
      return (
        this.user.password === this.repeatedPassword &&
        this.user.password.length > 0
      );
    },
    showPasswordNotCorrectMessage() {
      return (
        this.user.password !== this.repeatedPassword &&
        this.user.password.length > 0 &&
        this.repeatedPassword.length > 0
      );
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
