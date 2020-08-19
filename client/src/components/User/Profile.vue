<template>
  <div>
    <div class="paper">
      <h2>Profile page</h2>

      <li class="item">
        <span>Name:</span><span>{{ userData.name }}</span>
      </li>
      <li class="item">
        <span>Lastname:</span><span>{{ userData.lastName }}</span>
      </li>
      <li class="item">
        <span><i class="material-icons">person</i>Username:</span
        ><span>{{ userData.username }}</span>
      </li>
      <li class="item">
        <span><i class="material-icons">wc</i>Gender:</span
        ><span>{{ userData.gender }}</span>
      </li>
      <div class="buttons">
        <button
          class="btn waves-effect waves-light"
          name="action"
          @click="openEditForm"
        >
          Edit profile info
          <i class="material-icons right">create</i>
        </button>
        <button class="btn waves-effect waves-light" name="action">
          Change password
          <i class="material-icons right">settings</i>
        </button>
      </div>
    </div>
    <transition name="slide" type="animation" mode="out-in">
      <app-edit-profile
        :closeDialog="closeDialog"
        :closeDialogAndUpdateState="closeDialogAndUpdateState"
        :userData="userData"
        v-if="showEditDataPopup"
      ></app-edit-profile>
    </transition>
  </div>
</template>
<script>
import EditProfile from "./EditProfile.vue";
import Axios from "axios";
import { eventBus } from "../../main";
export default {
  data() {
    return {
      showEditDataPopup: false,
      userData: { username: "", name: "", lastName: "", gender: "" },
    };
  },
  components: {
    AppEditProfile: EditProfile,
  },
  methods: {
    openEditForm() {
      this.showEditDataPopup = true;

      document.querySelector("body").style.overflow = "hidden";
      window.scrollTo(0, 0);
    },
    closeDialog() {
      this.showEditDataPopup = false;
      document.querySelector("body").style.overflow = "scroll";
    },
    closeDialogAndUpdateState(newUserData) {
      this.userData = { ...this.userData, ...newUserData };
      this.closeDialog();
    },
  },
  async created() {
    try {
      const res = await Axios.get(`/api/user/${eventBus.currentUser.id}`);
      this.userData = { ...res.data };
    } catch (err) {
      console.log(err);
    }
  },
};
</script>

<style scoped>
.buttons {
  display: flex;
  justify-content: space-between;
}
.item {
  padding: 10px 5px 5px 5px;
  list-style: none;
  display: flex;
  justify-content: space-between;
  align-items: center;

  border-bottom: 1px solid #66626265;
}

.item:last-of-type {
  margin-bottom: 20px;
}

.paper {
  width: 70%;
  margin: 70px auto;
  box-shadow: -1px 3px 7px -1px rgba(0, 0, 0, 0.75);
  padding: 30px 60px;
}
</style>
