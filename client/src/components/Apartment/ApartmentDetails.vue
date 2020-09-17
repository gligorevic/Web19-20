<template>
  <div>
    <div style="position: relative;">
      <div style="margin-top: 20px;">
        <button @click="$router.go(-1)" class="waves-effect waves btn blue lighten-2">Back</button>
      </div>
      <h2 class="center">{{apartment.name}}</h2>
      <div class="price">
        {{apartment.pricePerNight}}€
        <sub>/night</sub>
      </div>
    </div>
    <hr />
    <app-gallery :images="apartment.images"></app-gallery>
    <div class="row">
      <div class="col s6" v-if="apartment && apartment.location">
        <ul class="collection with-header">
          <li class="collection-header">
            <h5>Location:</h5>
          </li>
          <li class="collection-item flex">
            <span>Country</span>
            <span>{{apartment.location.address.country}}</span>
          </li>
          <li class="collection-item flex">
            <span>City</span>
            <span>{{apartment.location.address.city}}</span>
          </li>
          <li class="collection-item flex">
            <span>Street</span>
            <span>{{apartment.location.address.street}} {{apartment.location.address.houseNumber}}</span>
          </li>
          <li class="collection-item flex">
            <span>Latitude</span>
            <span>{{apartment.location.latitude}}</span>
          </li>
          <li class="collection-item flex">
            <span>Longitude</span>
            <span>{{apartment.location.longitude}}</span>
          </li>
        </ul>
      </div>
      <div class="col s6" v-if="apartment && apartment.amenities && apartment.amenities.length > 0">
        <ul class="collection with-header">
          <li class="collection-header">
            <h5>Amenities:</h5>
          </li>
          <li
            v-for="amenity in apartment.amenities"
            :key="amenity.id"
            class="collection-item"
          >{{amenity.name}}</li>
        </ul>
      </div>
      <div class="col s6" v-if="apartment">
        <ul class="collection with-header">
          <li class="collection-header">
            <h5>Apartment info:</h5>
          </li>
          <li class="collection-item flex-center">
            <i class="small material-icons">sentiment_very_satisfied</i>
            <span style="margin-left: 3px;">
              Host:
              {{apartment.host.name}} {{apartment.host.lastName}}
            </span>
          </li>
          <li class="collection-item flex-center">
            <i class="small material-icons">person_pin</i>
            <span style="margin-left: 3px;">
              Guest number:
              <strong>{{apartment.guestNumber}}</strong>
            </span>
          </li>
          <li class="collection-item flex-center">
            <i class="small material-icons">hotel</i>
            <span style="margin-left: 3px;">
              Room number:
              <strong>{{apartment.roomNumber}}</strong>
            </span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Gallery from "./Gallery.vue";
export default {
  props: ["apartment"],

  components: {
    AppGallery: Gallery,
  },
};
</script>

<style scoped>
.flex {
  display: flex;
  justify-content: space-between;
}
.flex-center {
  display: flex;
  align-items: center;
}

hr {
  border: 0;
  height: 1px;
  background-image: linear-gradient(
    to right,
    rgba(0, 0, 0, 0),
    rgba(0, 0, 0, 0.75),
    rgba(0, 0, 0, 0)
  );
}
.price {
  position: absolute;
  top: 40%;
  right: 20px;
  padding: 7px;
  border-radius: 15px;
  background: #64b5f6;
  text-align: center;
  font-size: 25px;
  opacity: 0.9;
  transition: all 0.2s ease-in;
}
.price:hover {
  opacity: 1;
  transform: scale(1.03);
}
</style>