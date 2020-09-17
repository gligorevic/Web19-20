<template>
  <div class="container wrapper row">
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <h2>All Apartments</h2>
    </div>
    <hr />
    <div class="col s12 row">
      <app-apartment-card
        v-for="apartment in apartments"
        :key="apartment.id"
        :apartment="apartment"
        :deleteApartment="deleteApartment"
      ></app-apartment-card>
    </div>
  </div>
</template>


<script>
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
  methods: {
    deleteApartment(id) {
      this.apartments = this.apartments.filter((a) => a.id != id);
    },
  },
  async created() {
    try {
      const res = await Axios.get(`/api/apartment/admin/all`);
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