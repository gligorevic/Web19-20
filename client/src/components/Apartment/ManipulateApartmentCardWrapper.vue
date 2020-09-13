<template>
  <div class="col s4">
    <h1>{{apartment.name}}</h1>
    <app-apartment-card :appartment="appartment"></app-apartment-card>
  </div>
</template>
<script>
import ApartmentCard from "./ApartmentCard";

export default {
  props: ["apartment"],
  components: {
    AppApartmentCard: ApartmentCard,
  },
};
</script>
<style scoped>
</style>