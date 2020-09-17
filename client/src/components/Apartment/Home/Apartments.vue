<template>
  <div>
    <div class="row">
      <app-apartment-card
        v-for="apartment in apartments"
        :key="apartment.id"
        :apartment="apartment"
      ></app-apartment-card>
    </div>
  </div>
</template>

<script>
import ApartmentCard from "./ApartmentResWraper";

export default {
  props: ["apartments"],
  components: {
    AppApartmentCard: ApartmentCard,
  },
};
</script>

<style scoped>
</style>