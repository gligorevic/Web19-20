<template>
  <div class="userList">
    <div class="row">
      <div class="col s6">
        <h3>All reservations</h3>
      </div>
    </div>
    <div>
      <div class="row">
        <div class>
          <input class="search" type="text" v-model="search" placeholder="Search username.." />
        </div>
      </div>
      <div class="row">
        <div class="col s3">
          <a class="waves-effect waves-light btn" @click="sortReservations">Sort:{{sort}}</a>
        </div>
        <div class="col s9">
          <form action="#">
            Status:
            <input type="checkbox" id="Created" value="CREATED" v-model="statusFilter" />
            <label for="Created">Created</label>
            <input type="checkbox" id="Rejected" value="REJECTED" v-model="statusFilter" />
            <label for="Rejected">Rejected</label>
            <input type="checkbox" id="Withdrawn" value="WITHDRAWN" v-model="statusFilter" />
            <label for="Withdrawn">Withdrawn</label>
            <input type="checkbox" id="Accepted" value="ACCEPTED" v-model="statusFilter" />
            <label for="Accepted">Accepted</label>
            <input type="checkbox" id="Finished" value="FINISHED" v-model="statusFilter" />
            <label for="Finished">Finished</label>
          </form>
        </div>
      </div>
      <div class="divider"></div>
      <div class="row">
        <div class="col s12" v-for="res in filteredRes" :key="res.id">
          <div class="card blue-grey darken-1">
            <div class="card-content white-text">
              <span class="card-title">
                <u>{{res.guest.username}}'s Reservation</u>
              </span>
              <div class="row">
                <div class="col s3">
                  <p>
                    <u>User info:</u>
                    <br />
                    First Name: {{res.guest.name}}
                    <br />
                    Last Name: {{res.guest.lastName}}
                    <br />
                    Gender : {{res.guest.gender}}
                    <br />
                    Role : {{res.guest.role}}
                  </p>
                </div>
                <div class="col s3">
                  <u>Apartment info:</u>
                  <br/>
                  Apartment name: {{res.reservedApartment.name}}
                  <br />
                  Room number: {{res.reservedApartment.roomNumber}}
                  <br />
                  Number of nights : {{res.nightsNum}}
                  <br />
                  Price per night : ${{res.reservedApartment.pricePerNight}}
                </div>
                <div class="col s3">
                  <u>Reservation info:</u>
                  <br />
                  Date: {{new Date(res.startReservationDate)
                                                .toISOString()
                                                .split("T")[0]}}
                  <br />
                  Status: {{res.reservationStatus}}
                  <br />Total price:
                  <b>${{res.price}}</b>
                </div>
              </div>
              <div class="card-action">
                <a
                  v-if="checkStatusAccept(res)"
                  @click="changeStatus(res.id, 'ACCEPTED')"
                  class="waves-effect waves-light btn green"
                >Accept</a>
                <a
                  v-if="checkStatusReject(res)"
                  @click="changeStatus(res.id, 'REJECTED')"
                  class="waves-effect waves-light btn red"
                >Reject</a>
                <a
                  v-if="checkReservationDate(res)"
                  @click="changeStatus(res.id, 'FINISHED')"
                  class="waves-effect waves-light btn grey"
                >Finish</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { eventBus } from "@/main";
export default {
  data() {
    return {
      reservations: [],
      search: "",
      sort: "",
      statusFilter: [],
    };
  },
  async created() {
    try {
      Axios.get(`/api/reservations`).then(
        (response) => (this.reservations = response.data)
      );
    } catch (err) {
      console.log(err);
    }
  },
  methods: {
    async changeStatus(id, status) {
      try {
        
        await Axios.put(`/api/reservations/${id}/status`, status);
        this.reservations = this.reservations.map((res) =>
          res.id != id ? res : { ...res, reservationStatus: status }
        );

        eventBus.showMessage({
          message: "You have successfully changed reservation status !",
          type: "success",
        });
      } catch (error) {
        eventBus.showMessage({ message: error?.response?.data, type: "error" });
      }
    },
    sortReservations() {
      if (this.sort === "" || this.sort === "DESC") {
        this.sort = "ASC";
        this.reservations.sort(function (a, b) {
          return a.price - b.price;
        });
      } else {
        this.sort = "DESC";
        this.reservations.sort(function (a, b) {
          return b.price - a.price;
        });
      }
    },
    checkStatusAccept(res) {
      return (
        ["CREATED"].includes(res.reservationStatus) &&
        new Date(res.startReservationDate + res.nightsNum * 86400000) >
          Date.now()
      );
    },
    checkStatusReject(res) {
      return (
        ["CREATED", "ACCEPTED"].includes(res.reservationStatus) &&
        new Date(res.startReservationDate + res.nightsNum * 86400000) >
          Date.now()
      );
    },
    checkReservationDate(res) {
      return (
        new Date(res.startReservationDate + res.nightsNum * 86400000) <
          Date.now() && ["ACCEPTED"].includes(res.reservationStatus)
      );
    },
  },
  computed: {
    filteredRes: function () {
      return this.reservations.filter((reservation) => {
        if (this.statusFilter.length > 0) {
          return (
            reservation.guest.username
              .toLowerCase()
              .match(this.search.toLowerCase()) &&
            this.statusFilter.includes(reservation.reservationStatus)
          );
        } else {
          return reservation.guest.username
            .toLowerCase()
            .match(this.search.toLowerCase());
        }
      });
    },
  },
};
</script>


<style scoped>
.btnsize {
  width: 50%;
}

.buttonContainer {
  margin: 10px 10px;
}

label {
  padding: 0 20px;
  font-weight: bold;
}
.search {
  padding: 4px 12px;
  color: rgba(0, 0, 0, 0.7);
  border: 1px solid rgba(0, 0, 0, 0.12);
  transition: 0.15s all ease-in-out;
  background: white;
}
.collection {
  border: 0;
}
.collection-item {
  border: 0;
}

.userList {
  width: 70%;
  margin: 30px auto;
  padding: 30px 60px;
}
</style>
