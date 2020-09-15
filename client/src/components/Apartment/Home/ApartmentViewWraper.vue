<template>
  <div class="container">
    <app-apartment-details :apartment="apartment" v-if="apartment"></app-apartment-details>
    <app-comments></app-comments>
    <app-reservations></app-reservations>
  </div>
</template>

<script>
import Axios from "axios";
import ApartmentDetails from "../ApartmentDetails";
import Comments from "./Comments";
import Reservations from "./Reservations";

export default {
  data() {
    return { apartment: null };
  },
  components: {
    AppApartmentDetails: ApartmentDetails,
    AppComments: Comments,
    AppReservations: Reservations,
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