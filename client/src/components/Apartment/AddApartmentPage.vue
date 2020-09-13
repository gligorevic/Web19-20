<template>
  <div class="container center">
    <h2>Add apartment page</h2>
    <form class="row">
      <div class="col s4 formPart">
        <h5>Location:</h5>
        <div class="row">
          <div class="input-field col s6">
            <input id="country" type="text" class="validate" v-model="location.address.country" />
            <label for="country">Country</label>
          </div>
          <div class="input-field col s6">
            <input id="city" type="text" class="validate" v-model="location.address.city" />
            <label for="city">City</label>
          </div>

          <div class="input-field col s6">
            <input id="street" type="text" class="validate" v-model="location.address.street" />
            <label for="street">Street</label>
          </div>
          <div class="input-field col s3">
            <input
              id="houseNumber"
              type="number"
              class="validate"
              v-model="location.address.houseNumber"
            />
            <label for="houseNumber">HouseNum</label>
          </div>
          <div class="input-field col s3">
            <input id="postal" type="number" class="validate" v-model="location.address.postal" />
            <label for="postal">Postal</label>
          </div>
          <div class="input-field col s6">
            <input id="latitude" type="number" class="validate" v-model="location.latitude" />
            <label for="latitude">Latitude</label>
          </div>
          <div class="input-field col s6">
            <input id="longitude" type="number" class="validate" v-model="location.longitude" />
            <label for="longitude">Longitude</label>
          </div>
        </div>
      </div>
      <div class="col s7 formPart">
        <h5>Apartment info:</h5>
        <div class="row">
          <div class="input-field col s12">
            <input id="name" type="text" class="validate" v-model="apartment.name" />
            <label for="name">Name</label>
          </div>
          <div class="row col s12">
            <div class="col s4">Type:</div>
            <div class="col s4">
              <input
                name="group1"
                type="radio"
                id="apartment"
                value="APARTMENT"
                v-model="apartment.type"
              />
              <label for="apartment">Apartment</label>
            </div>
            <div class="col s4">
              <input name="group1" type="radio" id="room" value="ROOM" v-model="apartment.type" />
              <label for="room">Room</label>
            </div>
          </div>
          <div class="input-field col s6">
            <input id="rumnum" type="number" class="validate" v-model="apartment.roomNumber" />
            <label for="rumnum" class="active">Room number</label>
          </div>

          <div class="input-field col s6">
            <input id="guestNum" type="number" class="validate" v-model="apartment.guestNumber" />
            <label for="guestNum" class="active">Guest number</label>
          </div>
          <div class="input-field col s12">
            <input
              id="pricePerNight"
              type="number"
              class="validate"
              step="any"
              v-model="apartment.pricePerNight"
            />
            <label for="pricePerNight" class="active">Price per night</label>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      apartment: {
        name: "",
        type: "APARTMENT",
        roomNumber: 1,
        guestNumber: 1,
        startDate: new Date(),
        endDate: new Date(),
        pricePerNight: 0,
        checkInTime: "",
        checkOutTime: "",
        status: "INACTIVE",
      },
      location: {
        longitude: "",
        latitude: "",
        address: {
          city: "",
          country: "",
          postal: "",
          street: "",
          houseNumber: "",
        },
      },
    };
  },
};
</script>

<style scoped>
.formPart {
  padding: 15px 10px;
  border: 1px solid #1111112f;
  border-radius: 10px;
  margin: 10px;
  box-shadow: 0px 2px 2px -1px rgba(0, 0, 0, 0.514);
}
</style>