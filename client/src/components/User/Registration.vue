<template>
  <div class="row container center">
    <h2 class="addMargin">Registration</h2>
    <form class="col s6">
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
          <input id="email" type="email" class="validate" v-model="user.username" />
          <label for="email">Username</label>
          <span class="helper-text" data-error="wrong" data-success="right">Helper text</span>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="password" type="password" class="validate" v-model="user.password" />
          <label for="password">Password</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="repeatedPassword" type="password" class="validate" v-model="repeatedPassword" />
          <label for="repeatedPassword">Repeat password</label>
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
          <button class="btn btn-primary" @click.prevent="submit" :disabled="false">Submit!</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import Axios from "axios";

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
    };
  },
  methods: {
    async submit() {
      console.log(this.user);
      try {
        const res = await Axios.post("/api/user", {
          ...this.user,
        }).then((res) => console.log(res));
        console.log(res);
      } catch (error) {
        console.log(error.response);
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
</style>
