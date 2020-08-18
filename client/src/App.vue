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
</style>
