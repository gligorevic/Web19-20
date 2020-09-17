<template>
  <div class="filterWraper">
    <div>
      <h5>Type:</h5>
      <div>
        <input type="checkbox" value="APARTMENT" id="apartment" @input="setType" />
        <label for="apartment">Apartment</label>
      </div>
      <div>
        <input type="checkbox" value="ROOM" id="room" @input="setType" />
        <label for="room">Room</label>
      </div>
    </div>
    <div>
      <hr />
      <h5>Amenities:</h5>

      <div v-for="amenity in amenities" :key="amenity.name" style="text-align: left;">
        <input type="checkbox" :value="amenity.name" :id="amenity.name" @input="setAmenities" />
        <label :for="amenity.name">{{amenity.name}}</label>
      </div>
    </div>
    <div v-if="isUserHostOrAdmin">
      <h5>Activity:</h5>
    </div>
  </div>
</template>

<script>
import { eventBus } from "../../../main";
import Axios from "axios";

export default {
  props: ["filterByAmenities", "filterByTypes"],
  data() {
    return {
      amenities: [],
      filterByAmenity: [],
      filterByType: [],
    };
  },
  methods: {
    setType(e) {
      e.target.checked
        ? this.filterByType.push(e.target.value)
        : (this.filterByType = this.filterByType.filter(
            (type) => type != e.target.value
          ));
      this.filterByTypes(this.filterByType);
    },
    setAmenities(e) {
      e.target.checked
        ? this.filterByAmenity.push(e.target.value)
        : (this.filterByAmenity = this.filterByAmenity.filter(
            (amenity) => amenity != e.target.value
          ));
      this.filterByAmenities(this.filterByAmenity);
    },
  },
  computed: {
    isUserHostOrAdmin() {
      return (
        (eventBus.currentUser.role === "HOST" ||
          eventBus.currentUser.role === "ADMIN") &&
        this.$router.currentRoute.path != "/"
      );
    },
  },
  async beforeCreate() {
    try {
      const res = await Axios.get("/api/amenity");
      this.amenities = res.data;
    } catch (err) {
      console.log(err);
    }
  },
};
</script>

<style scoped>
.filterWraper {
  padding: 10px;
}

h5 {
  font-size: 20px;
}
</style>