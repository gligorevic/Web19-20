<template>
  <div class="container">
    <app-apartment-details :apartment="apartment" v-if="apartment"></app-apartment-details>
    <div class="row">
      <div v-if="apartment" class="col s4 offset-s4">
        <button
          class="waves-effect waves-light btn resButton blue lighten-2"
          name="action"
          @click="openReservationForm"
        >
          <i class="material-icons right">hotel</i>
          <i class="material-icons left">hotel</i>
          Make A Reservation
        </button>
      </div>
    </div>
    <transition name="slide" type="animation" mode="out-in">
      <create-reservation-page
        :datesRes="datesRes"
        :closeDialog="closeDialog"
        :apartment="apartment"
        v-if="showReservationForm"
      ></create-reservation-page>
    </transition>
    <app-comments :comments="comments" v-if="apartment" :hostId="apartment.host.id"></app-comments>
  </div>
</template>

<script>
import Axios from "axios";
import ApartmentDetails from "../ApartmentDetails";
import Comments from "./Comments";
import CreateReservationPage from "@/components/Reservation/CreateReservationPage.vue";

export default {
  data() {
    return {
      apartment: null,
      showReservationForm: false,
      comments: [],
      datesRes: [],
    };
  },
  methods: {
    openReservationForm() {
      this.showReservationForm = true;

      document.querySelector("body").style.overflow = "hidden";
      window.scrollTo(0, 0);
    },
    closeDialog() {
      this.showReservationForm = false;
      document.querySelector("body").style.overflow = "scroll";
    },
  },
  components: {
    AppApartmentDetails: ApartmentDetails,
    AppComments: Comments,
    CreateReservationPage: CreateReservationPage,
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
    try {
      const result = await Axios.get(
        "/api/reservations/" + this.$route.params.id
      );
      for (var x in result.data) {
        this.datesRes.push(new Date(parseInt(result.data[x], 10)));
      }
    } catch (err) {
      console.log(err);
    }
  },
};
</script>


<style scoped>
.resButton {
  display: block;
  width: 100%;
}
</style>