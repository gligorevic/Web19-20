<template>
  <div class="cover">
    <div class="container center">
      <div class="card formCard">
        <div @click.prevent="closeDialog" class="closeButton">
          <i class="material-icons">close</i>
        </div>
        <h2 class="addMargin">Make A Reservation</h2>
        <form>
          <div>
            <datepicker
              placeholder="Select Date"
              v-model="state.date"
              inline
              :selected-date="state.date"
              :disabled-dates="state.disabledDates"
            ></datepicker>
          </div>
          <div class="row">
            <span style="float:left">Leave a comment for your Host:</span>
            <textarea
              v-model="reservation.reservationMessage"
              class="textarea"
              id="textarea"
              name="textarea"
              rows="6"
              cols="50"
            ></textarea>
          </div>
          <div class="row">
            <div class="input-field col s4">
              <input
                id="nightsNum"
                type="number"
                min="1"
                class="validate"
                v-model="reservation.nightsNum"
              />
              <label for="nightsNum" class="active">Number of nights:</label>
            </div>
            <div class="input-field col s4 offset-s4">
              <label class="totalPrice">
                Total:
                <b>${{calculatePrice}}</b>
              </label>
              <br />
            </div>
          </div>
          <div class="row">
            <div class="col s4 offset-s4">
              <button
                class="waves-effect waves-light btn resButton blue lighten-2"
                name="action"
                @click.prevent="submit"
              >Submit</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { eventBus } from "@/main";
import Datepicker from "vuejs-datepicker";

export default {
  props: ["closeDialog", "apartment", "datesRes"],

  data() {
    return {
      state: {
        date: new Date(new Date().getTime() + 24 * 60 * 60 * 1000),
        disabledDates: {
          to: new Date(),
          dates: this.datesRes,
        },
      },
      reservation: {
        apartmentId: "",
        startReservationDate: "",
        nightsNum: undefined,
        price: 0,
        reservationMessage: "",
        guestId: 0,
        reservationStatus: "",
      },
    };
  },
  components: {
    Datepicker,
  },
  methods: {
    async submit() {
      try {
        this.reservation.startReservationDate = this.state.date
          .toISOString()
          .split("T")[0];
        this.reservation.nightsNum = parseInt(this.reservation.nightsNum);
        this.reservation.price = this.calculatePrice;
        await Axios.post(`/api/reservations`, this.reservation);
        eventBus.showMessage({
          message: "You have been successfully created reservation!",
          type: "success",
        });
        this.closeDialog();
      } catch (error) {
        eventBus.showMessage({ message: error?.response?.data, type: "error" });
        console.log(error);
      }
    },
  },
  created() {
    this.reservation.apartmentId = { ...this.apartment }.id;
    this.reservation.guestId = parseInt(eventBus.currentUser.id, 10);
  },
  computed: {
    calculatePrice() {
      let a = { ...this.apartment }.pricePerNight * this.reservation.nightsNum;
      if (isNaN(a)) {
        return 0;
      } else {
        return a;
      }
    },
  },
};
</script>

<style scoped>
.closeButton {
  position: absolute;
  top: 12px;
  right: 15px;
  cursor: pointer;
}

.textarea {
  height: 100px;
  color: black;
  background-color: white;
}

.resButton {
  display: block;
  width: 100%;
}

.cover {
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  z-index: 100;
  animation: fade-in 1s linear;
  background: #25252562;
}
.formCard {
  height: 100%; /* ISPRAVITI NA DINAMCKI */
  min-width: 50%;
  padding: 0 60px;
  margin-top: 30px;
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
.example {
  background: #f2f2f2;
  border: 1px solid #ddd;
  padding: 0em 1em 1em;
  margin-bottom: 2em;
}

.center {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.addMargin {
  margin-top: 30px;
  margin-bottom: 40px;
}
</style>