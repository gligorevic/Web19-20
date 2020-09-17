<template>
  <div class="row">
    <div class="row col s3 searchItem">
      <div class="col s12">
        <h5>Price:</h5>
        <p class="range-field">
          <label for="priceFrom">
            Price from:
            <strong>{{ priceFrom}}€</strong>
          </label>
          <input
            type="range"
            :value="priceFrom"
            id="priceFrom"
            min="0"
            max="100"
            @change="changePriceFrom"
          />
        </p>
      </div>
      <div class="col s12">
        <p class="range-field">
          <label for="priceTo">
            Price to:
            <strong>{{ priceTo == "100" ? "100+" : priceTo}}€</strong>
          </label>
          <input
            type="range"
            :value="priceTo"
            id="priceTo"
            min="0"
            max="100"
            @change="changePriceTo"
          />
        </p>
      </div>
    </div>
    <div class="row col s3 searchItem">
      <div class="col s12">
        <h5>Location:</h5>
        <div class="input-field">
          <input id="country" type="text" class="validate" v-model="country" />
          <label for="country">Country</label>
        </div>
      </div>
      <div class="col s12">
        <div class="input-field" style="padding-bottom: 20px">
          <input id="city" type="text" class="validate" v-model="city" />
          <label for="city">City</label>
        </div>
      </div>
    </div>
    <div class="row col s3 searchItem">
      <div class="col s12">
        <h5>Room number:</h5>
        <p class="range-field">
          <label for="minRoomNumber">
            Min room number:
            <strong>{{ minRoomNumber}}</strong>
          </label>
          <input
            type="range"
            :value="minRoomNumber"
            id="minRoomNumber"
            min="0"
            max="100"
            @change="changeMinRoomNumber"
          />
        </p>
      </div>
      <div class="col s12">
        <p class="range-field">
          <label for="maxRoomNumber">
            Max room number:
            <strong>{{ maxRoomNumber}}</strong>
          </label>
          <input
            type="range"
            :value="maxRoomNumber"
            id="maxRoomNumber"
            min="0"
            max="100"
            @change="changeMaxRoomNumber"
          />
        </p>
      </div>
    </div>
    <div class="row col s3 searchItem" style="border: none;">
      <div class="col s12">
        <h5>Guest number:</h5>
        <div class="input-field">
          <input id="guestNum" type="number" class="validate" min="1" v-model="guestNumber" />
          <label for="guestNum" class="active">Guest number</label>
        </div>
      </div>
    </div>
    <div class="col s12 searchButton">
      <button class="waves-effect waves-light btn blue lighten-2" @click.prevent="submit">Search</button>
    </div>
  </div>
</template>

<script>
export default {
  props: ["submitSearch"],
  data() {
    return {
      priceFrom: 0,
      priceTo: 100,
      city: "",
      country: "",
      minRoomNumber: 0,
      maxRoomNumber: 10,
      guestNumber: 1,
    };
  },

  methods: {
    submit() {
      this.submitSearch({
        priceFrom: this.priceFrom,
        priceTo: this.priceTo,
        city: this.city,
        country: this.country,
        minRoomNumber: this.minRoomNumber,
        maxRoomNumber: this.maxRoomNumber,
        guestNumber: this.guestNumber,
      });
    },
    changeMinRoomNumber(e) {
      this.minRoomNumber = e.target.value;
      if (Number(this.minRoomNumber) > Number(this.maxRoomNumber))
        this.maxRoomNumber = this.minRoomNumber;
    },
    changeMaxRoomNumber(e) {
      this.maxRoomNumber = e.target.value;
      if (Number(e.target.value) < Number(this.minRoomNumber))
        this.minRoomNumber = e.target.value;
    },
    changePriceFrom(e) {
      this.priceFrom = e.target.value;
      if (Number(this.priceFrom) > Number(this.priceTo))
        this.priceTo = this.priceFrom;
    },
    changePriceTo(e) {
      this.priceTo = e.target.value;
      if (Number(e.target.value) < Number(this.priceFrom))
        this.priceFrom = e.target.value;
    },
  },
};
</script>

<style scoped>
.searchItem {
  border-right: 1px dashed #5353533d;
  margin-bottom: 0;
  margin-top: 20px;
  height: 100%;
}

.searchButton {
  border-top: 1px dashed #5353533d;
  display: flex;
  align-items: center;
  flex-direction: row-reverse;
  padding-top: 15px;
}
</style>