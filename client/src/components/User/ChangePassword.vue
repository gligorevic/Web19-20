<template>
  <div class="cover">
    <div class="container center">
      <div class="row card formCard">
        <div @click.prevent="closeDialog" class="closeButton">
          <i class="material-icons">close</i>
        </div>
        <h2 class="addMargin">Change profile password</h2>
        <form class="col s12">
          <div class="row">
            <div class="input-field col s12">
              <input
                id="oldPassword"
                :class="{invalid: triedOldPassword.length > 0 && oldPassword === triedOldPassword}"
                type="password"
                v-model="oldPassword"
              />
              <label for="oldPassword">Old password</label>
              <span
                v-if="triedOldPassword.length > 0 && oldPassword === triedOldPassword"
              >Old password is incorect.</span>
            </div>
          </div>
          <div class="row">
            <div class="input-field col s12">
              <input
                id="newPassword"
                type="password"
                :class="{valid: isPasswordMatching, invalid: checkIfNotMatching }"
                v-model="newPassword"
              />
              <label for="newPassword">New password</label>
            </div>
          </div>
          <div class="row">
            <div class="input-field col s12">
              <input
                id="repeatedPasswordForChange"
                type="password"
                :class="{valid: isPasswordMatching, invalid: checkIfNotMatching }"
                v-model="repeatedPassword"
              />
              <label for="repeatedPassword">Repeat new password</label>
              <span
                class="helper-text"
                data-error="wrong"
                data-success="right"
                v-if="checkIfNotMatching"
              >Passwords must match.</span>
            </div>
          </div>
          <div class="row">
            <div class="col s12">
              <button
                class="btn btn-primary"
                @click.prevent="submit"
                :disabled="isSomeFieldEmpty || checkIfNotMatching"
              >Change password</button>
            </div>
            <span class="badge" v-if="isSomeFieldEmpty">Fields can't stay empty!</span>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { eventBus } from "../../main";

export default {
  props: ["closeDialog"],
  data() {
    return {
      repeatedPassword: "",
      oldPassword: "",
      newPassword: "",
      triedOldPassword: "",
    };
  },
  methods: {
    async submit() {
      try {
        await Axios.put(`/api/user/${eventBus.currentUser.id}/password`, {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword,
        });

        eventBus.showMessage({
          message: "You have been successfully changed password!",
          type: "success",
        });
        this.closeDialog();
      } catch (error) {
        this.triedOldPassword = this.oldPassword;
        eventBus.showMessage({ message: error?.response?.data, type: "error" });
      }
    },
  },
  computed: {
    checkIfNotMatching() {
      return (
        this.newPassword.length > 0 &&
        this.newPassword !== this.repeatedPassword
      );
    },
    isSomeFieldEmpty() {
      return this.oldPassword.length === 0 || this.newPassword.length === 0;
    },
    isPasswordMatching() {
      return (
        this.newPassword === this.repeatedPassword &&
        this.newPassword.length > 0
      );
    },
  },
};
</script>

<style scoped>
.closeButton {
  position: absolute;
  top: 12px;
  right: 15px;
  cursor: pointer;
}

.cover {
  position: absolute;
  bottom: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  z-index: 100;
  animation: fade-in 1s linear;
  background: #25252562;
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

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
