<template>
  <div class="cover">
    <div class="container center">
      <div class="row card formCard">
        <div @click.prevent="closeDialog" class="closeButton">
          <i class="material-icons">close</i>
        </div>
        <h2 class="addMargin">Change profile data</h2>
        <form class="col s12">
          <div class="row">
            <div class="input-field col s6">
              <input
                id="first_name"
                type="text"
                class="validate"
                v-model="user.name"
              />
              <label for="first_name">First Name</label>
            </div>
            <div class="input-field col s6">
              <input
                id="last_name"
                type="text"
                class="validate"
                v-model="user.lastName"
              />
              <label for="last_name">Last Name</label>
            </div>
          </div>

          <div class="row">
            <div class="col s3">Gender:</div>
            <div class="col s3">
              <input
                name="group1"
                type="radio"
                id="male"
                value="MALE"
                v-model="user.gender"
              />
              <label for="male">Male</label>
            </div>
            <div class="col s3">
              <input
                name="group1"
                type="radio"
                id="female"
                value="FEMALE"
                v-model="user.gender"
              />
              <label for="female">Female</label>
            </div>
            <div class="col s3">
              <input
                name="group1"
                type="radio"
                id="other"
                value="OTHER"
                v-model="user.gender"
              />
              <label for="other">Other</label>
            </div>
          </div>
          <div class="row">
            <div class="col s12">
              <button
                class="btn btn-primary"
                @click.prevent="submit"
                :disabled="isSomeFieldEmpty"
              >
                Submit!
              </button>
            </div>
            <span class="badge" v-if="isSomeFieldEmpty"
              >Fields can't stay empty!</span
            >
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
  props: ["closeDialog", "userData", "closeDialogAndUpdateState"],
  data() {
    return {
      user: {
        name: "",
        lastName: "",
        gender: "MALE",
      },
    };
  },
  methods: {
    async submit() {
      console.log(this.user);
      try {
        await Axios.put(`/api/user/${eventBus.currentUser.id}`, this.user);

        eventBus.showMessage({
          message: "You have been successfully changed profile data!",
          type: "success",
        });
        this.closeDialogAndUpdateState(this.user);
      } catch (error) {
        eventBus.showMessage({ message: error?.response?.data, type: "error" });
      }
    },
  },
  created() {
    this.user = { ...this.userData };
  },
  computed: {
    isSomeFieldEmpty() {
      return !this.user.name || !this.user.lastName;
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
