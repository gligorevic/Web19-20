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
                  Date: {{res.startReservationDate}}
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
              <div class="row" v-if="commentArea && res.id===commentShowId">
                <form action="#">
                  Rate your stay:
                  <input type="radio" id="star1" value="1" v-model="rating" />
                  <label for="star1">Awful</label>
                  <input type="radio" id="star2" value="2" v-model="rating" />
                  <label for="star2">Bad</label>
                  <input type="radio" id="star3" value="3" v-model="rating" />
                  <label for="star3">OK</label>
                  <input type="radio" id="star4" value="4" v-model="rating" />
                  <label for="star4">Very nice</label>
                  <input type="radio" id="star5" value="5" v-model="rating" />
                  <label for="star5">Perfect!</label>
                </form>
                <textarea
                  v-model="comment.text"
                  class="textarea"
                  id="textarea"
                  name="textare"
                  rows="6"
                  cols="50"
                ></textarea>
                <div class="col s2 offset-s5">
                  <a class="waves-effect waves-light btn green" @click="submitComment(res)">Submit</a>
                </div>
              </div>
              <div class="card-action">
                <a
                  v-if="checkStatusWithdraw(res)"
                  @click="changeStatus(res.id, 'WITHDRAWN')"
                  class="waves-effect waves-light red btn"
                >Withdraw</a>
                <a
                  v-if="['FINISHED', 'REJECTED'].includes(res.reservationStatus) && res.commented === false"
                  @click="showCommentArea(res.id)"
                  class="waves-effect waves-light btn grey"
                >Comment</a>
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
      commentArea: false,
      commentShowId: undefined,
      rating: "",
      comment: {
        text: "",
        grade: 0,
        apartmentId: undefined,
        reservationId: undefined,
      },
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
    async submitComment(res) {
      try {
        this.comment.apartmentId = res.reservedApartment.id;
        this.comment.reservationId = res.id;
        this.comment.grade = parseInt(this.rating, 10);
        await Axios.post(`/api/comments`, this.comment);

        this.reservations = this.reservations.map((r) =>
          res.id !== r.id ? r : { ...r, commented: true }
        );

        eventBus.showMessage({
          message: "You have successfully added comment!",
          type: "success",
        });
        this.showCommentArea();
      } catch (error) {
        eventBus.showMessage({ message: error?.response?.data, type: "error" });
        console.log(error);
      }
    },
    async changeStatus(id, status) {
      try {
        this.reservations = this.reservations.map((res) =>
          res.id != id ? res : { ...res, reservationStatus: status }
        );
        await Axios.put(`/api/reservations/${id}/status`, status);

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
    showCommentArea(res) {
      this.commentArea = !this.commentArea;
      this.commentShowId = res;
      this.comment.text = "";
      this.rating = 0;
    },
    checkStatusWithdraw(res) {
      return ["CREATED", "ACCEPTED"].includes(res.reservationStatus);
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
.textarea {
  color: black;
  background-color: white;
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
