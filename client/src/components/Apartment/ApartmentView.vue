<template>
  <div class="container">
    <app-apartment-details :apartment="apartment" v-if="apartment"></app-apartment-details>
  </div>
</template>

<script>
import Axios from "axios";
import ApartmentDetails from "./ApartmentDetails";

export default {
  data() {
    return { apartment: null };
  },
  components: {
    AppApartmentDetails: ApartmentDetails,
  },
  async beforeCreate() {
    try {
      const res = await Axios.get("/api/apartment/" + this.$route.params.id);
      this.apartment = res.data;
    } catch (err) {
      console.log(err);
    }
  },
};
</script>

<style scoped>
</style>