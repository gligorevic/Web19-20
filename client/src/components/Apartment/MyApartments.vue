<template>
  <div class="container wrapper row">
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <h2>My Apartments</h2>

      <router-link
        to="/addApartment"
        tag="a"
        exact
        class="btn-floating btn-large waves-effect waves-light green"
      >
        <i class="material-icons">add</i>
      </router-link>
    </div>
    <hr />
    <div class="col s12 row">
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
.col {
  padding: 0;
}

.wrapper {
  padding: 30px;
}
</style>