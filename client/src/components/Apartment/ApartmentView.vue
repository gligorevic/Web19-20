<template>
  <div class="container">
    <app-apartment-details :apartment="apartment" v-if="apartment"></app-apartment-details>
    <app-comments :comments="comments" v-if="apartment" :hostId="apartment.host.id"></app-comments>
  </div>
</template>

<script>
import Axios from "axios";
import ApartmentDetails from "./ApartmentDetails";
import Comments from "./Home/Comments";

export default {
  data() {
    return { apartment: null, comments: [] };
  },
  components: {
    AppApartmentDetails: ApartmentDetails,
    AppComments: Comments,
  },
  async beforeCreate() {
    try {
      const res = await Axios.get("/api/apartment/" + this.$route.params.id);
      this.apartment = res.data;
    } catch (err) {
      console.log(err);
    }
  },
  async created() {
    try {
      const res = await Axios.get("/api/comments/" + this.$route.params.id);
      this.comments = res.data;
    } catch (err) {
      console.log(err);
    }
  },
};
</script>

<style scoped>
</style>