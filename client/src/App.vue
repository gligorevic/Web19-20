<template>
  <div id="app">
    <AppHeader></AppHeader>
    <router-view></router-view>
  </div>
</template>

<script>
import AppHeader from "./components/layout/Header.vue";

export default {
  name: "App",
  components: {
    AppHeader,
  },
};
</script>

<style></style>
