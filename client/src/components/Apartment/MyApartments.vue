<template>
  <div class="container wrapper">
    <h2>My Apartments</h2>
    <hr />
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
import { eventBus } from "../../main";
import Axios from "axios";
import ApartmentCard from "./ManipulateApartmentCardWrapper";

export default {
  data() {
    return {
      apartments: [],
    };
  },
  components: {
    AppApartmentCard: ApartmentCard,
  },
  async created() {
    try {
      const res = await Axios.get(
        `/api/apartment/user/${eventBus.currentUser.id}`
      );
      console.log(res);
      this.apartments = res.data;
    } catch (err) {
      console.log(err);
    }
  },
};
</script>
<style scoped>
.wrapper {
  padding: 30px;
}
</style>