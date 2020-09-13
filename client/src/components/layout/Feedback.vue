<template>
  <transition name="slide" type="animation" mode="out-in">
    <div
      class="feedbackPopup"
      :class="{success: type === 'success', error: type === 'error'}"
      v-if="!hide"
    >
      <p>{{message}}</p>
      <div class="close" @click="hide = true">
        <i class="small material-icons">cancel</i>
      </div>
    </div>
  </transition>
</template>

<script>
import { eventBus } from "../../main";
export default {
  data() {
    return { message: "", hide: true, type: "success" };
  },
  created() {
    eventBus.$on("showMessage", (msgObj) => {
      this.message = msgObj.message;
      this.type = msgObj.type;
      this.hide = false;
      var vm = this;
      setTimeout(() => {
        vm.hide = true;
      }, 6000);
    });
  },
};
</script>

<style scoped>
.feedbackPopup {
  z-index: 130;
  padding: 30px;
  position: absolute;
  bottom: 40px;
  right: 40px;
  background: white;
  border: 1px solid rgba(122, 122, 122, 0.548);
  box-shadow: -2px 4px 5px 0px rgba(0, 0, 0, 0.75);
  width: 22%;
}
.success {
  background: #92f09288;
}

.error {
  background: #f5513b8e;
}

.close {
  cursor: pointer;
  position: absolute;
  right: 4px;
  top: 4px;
}

.close:hover {
  transition: transform 0.3s;
  transform: scale(1.14);
}
</style>