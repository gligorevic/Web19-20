<template>
  <div id="app">
    <AppHeader></AppHeader>
    <router-view></router-view>
    <AppFeedback></AppFeedback>
  </div>
</template>

<script>
import AppHeader from "./components/layout/Header.vue";
import AppFeedback from "./components/layout/Feedback.vue";

export default {
  name: "App",
  components: {
    AppHeader,
    AppFeedback,
  },
};
</script>

<style>
#id {
  position: relative;
}
.slide-enter {
  opacity: 0;
}
.slide-enter-active {
  animation: slide-in 1s ease-out forwards;
  transition: opacity 0.5s;
}

.slide-leave {
}

.slide-leave-active {
  animation: slide-out 1s ease-out forwards;
  transition: opacity 1s;
  opacity: 0;
  position: absolute;
}

.slide-move {
  transition: transform 1s;
}

@keyframes slide-in {
  from {
    transform: translateY(20px);
  }
  to {
    transform: translateY(0);
  }
}

@keyframes slide-out {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(20px);
  }
}
</style>
