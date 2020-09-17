<template>
  <div>
    <img
      :src="apartment.images && '/server/api/' + apartment.images[0]"
      class="coverImg"
    />
    <div class="apartmentInfo">
      <h5>{{ apartment.name }}</h5>
      <div class="flex-center">
        <i class="small material-icons">location_city</i>
        <span style="margin-left: 3px;"
          >{{ apartment.address.city }}, {{ apartment.address.street }}
          {{ apartment.address.houseNumber }}</span
        >
      </div>
      <div class="flex-center">
        <i class="small material-icons">people</i>
        <span style="margin-left: 3px;"
          >Guests: {{ apartment.guestNumber }}</span
        >
      </div>
      <div class="flex-center">
        <i class="small material-icons">hotel</i>
        <span style="margin-left: 3px;">Rooms: {{ apartment.roomNumber }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["apartment"],
  created() {
    console.log(this.apartment);
  },
};
</script>

<style scoped>
.coverImg {
  max-width: 100%;
  opacity: 0.8;
  transition: all 0.2s ease-in;
  cursor: pointer;
}

.coverImg:hover {
  opacity: 1;
}

.flex-center {
  display: flex;
  align-items: center;
}

.apartmentInfo {
  padding: 2px 10px;
}
</style>
