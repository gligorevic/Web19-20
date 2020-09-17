<template>
  <div class="container center">
    <h2>Create new apartment</h2>
    <form
      class="row"
      style="display:flex; align-items: stretch; flex-wrap: wrap; justify-content: space-between;"
    >
      <div class="col s4 formPart">
        <h5>Location:</h5>
        <div class="row">
          <div class="input-field col s6">
            <input
              id="country"
              type="text"
              class="validate"
              v-model="location.address.country"
            />
            <label for="country">Country</label>
          </div>
          <div class="input-field col s6">
            <input
              id="city"
              type="text"
              class="validate"
              v-model="location.address.city"
            />
            <label for="city">City</label>
          </div>

          <div class="input-field col s6">
            <input
              id="street"
              type="text"
              class="validate"
              v-model="location.address.street"
            />
            <label for="street">Street</label>
          </div>
          <div class="input-field col s3">
            <input
              id="houseNumber"
              type="number"
              class="validate"
              min="0"
              v-model="location.address.houseNumber"
            />
            <label for="houseNumber">HouseNum</label>
          </div>
          <div class="input-field col s3">
            <input
              id="postal"
              type="number"
              min="0"
              class="validate"
              v-model="location.address.postal"
            />
            <label for="postal">Postal</label>
          </div>
          <div class="input-field col s6">
            <input
              id="latitude"
              type="number"
              class="validate"
              v-model="location.latitude"
            />
            <label for="latitude">Latitude</label>
          </div>
          <div class="input-field col s6">
            <input
              id="longitude"
              type="number"
              class="validate"
              v-model="location.longitude"
            />
            <label for="longitude">Longitude</label>
          </div>
        </div>
      </div>
      <div class="col s1" style="width: 20px;"></div>
      <div class="col s7 formPart">
        <h5>Apartment info:</h5>
        <div class="row">
          <div class="input-field col s12">
            <input
              id="name"
              type="text"
              class="validate"
              v-model="apartment.name"
            />
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
              <input
                name="group1"
                type="radio"
                id="room"
                value="ROOM"
                v-model="apartment.type"
              />
              <label for="room">Room</label>
            </div>
          </div>
          <div class="input-field col s6">
            <input
              id="rumnum"
              type="number"
              class="validate"
              min="1"
              v-model="apartment.roomNumber"
            />
            <label for="rumnum" class="active">Room number</label>
          </div>

          <div class="input-field col s6">
            <input
              id="guestNum"
              type="number"
              class="validate"
              min="1"
              v-model="apartment.guestNumber"
            />
            <label for="guestNum" class="active">Guest number</label>
          </div>
          <div class="input-field col s12">
            <input
              id="pricePerNight"
              type="number"
              class="validate"
              step="any"
              min="1"
              v-model="apartment.pricePerNight"
            />
            <label for="pricePerNight" class="active">Price per night</label>
          </div>
        </div>
      </div>
      <div class="col s7 formPart">
        <h5>Dates:</h5>
        <div class="row">
          <div class="input-field col s6">
            <input
              id="startDate"
              type="date"
              v-model="apartment.startDate"
              :min="today"
            />
            <label for="startDate" class="active">Start date</label>
          </div>
          <div class="input-field col s6">
            <input
              id="endDate"
              type="date"
              v-model="apartment.endDate"
              :min="apartment.startDate"
            />
            <label for="endDate" class="active">End date</label>
          </div>
        </div>
      </div>
      <div class="col s1" style="width: 20px;"></div>
      <div class="col s4 formPart">
        <h5>Checkin/out:</h5>
        <div class="row">
          <div class="input-field col s6">
            <input
              id="checkInTime"
              type="time"
              v-model="apartment.checkInTime"
            />
            <label for="checkInTime" class="active">Checkin</label>
          </div>
          <div class="input-field col s6">
            <input
              id="checkOutTime"
              type="time"
              v-model="apartment.checkOutTime"
            />
            <label for="checkOutTime" class="active">Checkout</label>
          </div>
        </div>
      </div>
      <div class="col s4 formPart">
        <h5>Amenities:</h5>
        <div
          v-for="amenity in allAmenities"
          :key="amenity.name"
          style="text-align: left;"
        >
          <input
            type="checkbox"
            :value="amenity.name"
            :id="amenity.name"
            @input="setAmenities"
          />
          <label :for="amenity.name">{{ amenity.name }}</label>
        </div>
      </div>
      <div class="col s1" style="width: 20px;"></div>
      <div class="col s7 formPart">
        <h5>Images:</h5>
        <div v-for="image in images" :key="image.name" class="imageBox">
          <img :src="image.url" />
        </div>
        <div>
          <input
            accept="image/*"
            id="file-upload"
            type="file"
            multiple
            @change="handleChangeImages"
            style="display: none;"
          />
          <label
            for="file-upload"
            style="display: flex; align-items: center; justify-content: flex-end; cursor: pointer;"
          >
            <i class="material-icons" style="color: green;">add</i>
            <span style="color: black;">Add pictures</span>
          </label>
        </div>
      </div>
      <div class="col s12">
        <span class="message" v-if="isSomeFieldsEmpty"
          >Fields can't stay empty!</span
        >
      </div>
      <div class="col s12">
        <button
          class="waves-effect waves-light btn blue lighten-2"
          @click.prevent="submit"
          :disabled="isSomeFieldsEmpty"
        >
          Add apartment
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import { eventBus } from "../../main";
import Axios from "axios";
export default {
  data() {
    return {
      allAmenities: [],
      today: new Date().toISOString().split("T")[0],
      images: [],
      apartment: {
        name: "",
        type: "APARTMENT",
        roomNumber: 1,
        guestNumber: 1,
        startDate: new Date().toISOString().split("T")[0],
        endDate: new Date().toISOString().split("T")[0],
        pricePerNight: 1,
        checkInTime: "",
        checkOutTime: "",
        amenities: [],
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
  methods: {
    setAmenities(e) {
      console.log(e.target.value);
      e.target.checked
        ? this.apartment.amenities.push(
            this.allAmenities.find((a) => a.name == e.target.value)
          )
        : this.apartment.filter((a) => a.name != e.target.value);
    },
    handleChangeImages(e) {
      this.images = Array.from(e.target.files).map((file) => ({
        url: URL.createObjectURL(file),
        file,
        name: file.name,
      }));
    },
    async submit() {
      try {
        console.log(this.apartment, this.location);
        let formData = new FormData();

        formData.append(
          "apartment",
          new Blob(
            [
              JSON.stringify({
                ...this.apartment,
                checkInTime: new Date().setHours(
                  this.apartment.checkInTime.split(":")[0],
                  this.apartment.checkInTime.split(":")[1]
                ),
                checkOutTime: new Date().setHours(
                  this.apartment.checkOutTime.split(":")[0],
                  this.apartment.checkOutTime.split(":")[1]
                ),
                location: this.location,
              }),
            ],
            {
              type: "application/json",
            }
          )
        );
        this.images.forEach((img) => formData.append("file", img.file));

        const res = await Axios.post("/api/apartment", formData);
        console.log(res);
        eventBus.showMessage({
          message: "Successfully added apartment",
          type: "success",
        });
        this.$router.push("/myApartments");
      } catch (error) {
        // eventBus.showMessage({ message: error?.response?.data, type: "error" });
        console.log(error);
      }
    },
  },
  async created() {
    try {
      const res = await Axios.get("/api/amenity");
      this.allAmenities = res.data;
    } catch (err) {
      console.log(err);
    }
  },
  computed: {
    isSomeFieldsEmpty() {
      return (
        !this.apartment.name.length ||
        !this.apartment.checkInTime ||
        !this.apartment.checkOutTime ||
        !this.location.longitude ||
        !this.location.latitude ||
        !this.location.address.city ||
        !this.location.address.country ||
        !this.location.address.postal ||
        !this.location.address.street ||
        !this.location.address.houseNumber
      );
    },
  },
};
</script>

<style scoped>
.formPart {
  padding: 20px 36px;
  border: 1px solid #1111112f;
  border-radius: 10px;
  box-shadow: 0px 2px 2px -1px rgba(0, 0, 0, 0.514);
  margin-bottom: 15px;
  flex-grow: 1;
}

.imageBox {
  width: 30%;
  margin: 4px 1.33%;
  transition: opacity 0.25s ease-in;
  opacity: 0.7;
  display: inline-block;
}

.imageBox:hover {
  opacity: 1;
}

.imageBox img {
  border: 1px solid #1f1f1f48;
  max-width: 100%;
}

.message {
  color: darkgray;
}
</style>
