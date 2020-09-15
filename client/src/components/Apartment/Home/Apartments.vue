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
import Axios from "axios";
import ApartmentCard from "./ApartmentResWraper";

export default {
  components: {
    AppApartmentCard: ApartmentCard,
  },
  data() {
    return { apartments: [] };
  },
  async beforeCreate() {
    try {
      const res = await Axios.get("/api/apartment");
      this.apartments = res.data;
    } catch (err) {
      console.log(err);
    }
  },
};
</script>

<style scoped>
</style>