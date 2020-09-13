<template>
    <div class="userList">
      <div class="row">
        <div class="col s6">
          <h3>
          All reservations
          </h3>
        </div>
      </div>   
      <div>
        <div class="row">
          <div class="">
            <input class="search" type="text" v-model="search" placeholder="Search username.."/> 
          </div>
        </div>  
        <div class="row">
          <div class = "col s3">
            <a class="waves-effect waves-light btn"
              @click = sortReservations
              >Sort:{{sort}}
            </a>
          </div>
          <div class="col s9">
            <form action="#" >
              Status:
              <input type="checkbox" id="Created" value="CREATED" v-model="statusFilter">
              <label for="Created">Created</label>
              <input type="checkbox" id="Rejected" value="REJECTED" v-model="statusFilter">
              <label for="Rejected">Rejected</label>
              <input type="checkbox" id="Withdrawn" value="WITHDRAWN" v-model="statusFilter">
              <label for="Withdrawn">Withdrawn</label>
              <input type="checkbox" id="Accepted" value="ACCEPTED" v-model="statusFilter">
              <label for="Accepted">Accepted</label>
              <input type="checkbox" id="Finished" value="FINISHED" v-model="statusFilter">
              <label for="Finished">Finished</label>
            </form>
          </div>
        </div>
        <div class="divider">
        </div>
        <div class="row">
          <div class="col s12" v-for="res in filteredRes" :key="res.id">
            <div class="card blue-grey darken-1">
              <div class="card-content white-text">
                <span class="card-title"><u>{{res.guest.username}}'s Reservation</u></span>
                  <div class="row">
                    <div class="col s3">
                      <p>
                        <u>
                        User info:</u>
                        <br/>
                        First Name: {{res.guest.name}}
                        <br/>
                        Last Name: {{res.guest.lastName}}
                        <br/>
                        Gender : {{res.guest.gender}}
                        <br/>
                        Role : {{res.guest.role}}
                      </p>
                    </div>
                     <div class="col s3">
                       <u>
                        Apartment info:</u>
                       <br/>
                        Room number: {{res.reservedApartment.roomNumber}}
                       <br/>
                        Number of nights : {{res.nightsNum}}
                       <br/>
                        Price per night : ${{res.reservedApartment.pricePerNight}}
                    </div>
                    <div class="col s3">
                      <u>
                      Reservation info:</u>
                      <br/>
                      <br/>
                      Status: {{res.reservationStatus}}
                      <br/>
                      Total price:<b> ${{res.price}} </b>
                    </div>
                  </div>
                <div class="card-action">
                   <a v-if="checkStatusWithdraw(res)" class="waves-effect waves-light red btn ">Withdraw</a>
                   <a v-if="checkStatusComment(res)" class="waves-effect waves-light btn grey">Comment</a>
                </div>  
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import reservations from '@/mock/mockRes'

export default {
  data(){
    return{
      reservations,
      search:'',
      sort:'',
      statusFilter:[],
    }
  },
  methods: {
    sortReservations(){
      if(this.sort === '' || this.sort === 'DESC'){
        this.sort = 'ASC';
        this.reservations.sort(function(a,b){
          return a.price - b.price;
        })
      }else {
        this.sort = 'DESC';
        this.reservations.sort(function(a,b){
          return b.price - a.price;
        })
      }
    },
    checkStatusWithdraw(res){
      return ['CREATED' , 'ACCEPTED'].includes(res.reservationStatus);
    },
    checkStatusComment(res){
      return['FINISHED' , 'REJECTED'].includes(res.reservationStatus);
    }
  },
  computed: {
    filteredRes: function(){
      return this.reservations.filter((reservation) =>{
        if(this.statusFilter.length > 0){
        return reservation.guest.username.toLowerCase().match(this.search.toLowerCase()) && 
         this.statusFilter.includes(reservation.reservationStatus); 
        }else{ 
         return reservation.guest.username.toLowerCase().match(this.search.toLowerCase());
        }
      })
    }
  }
}
</script>


<style scoped>
label{
  padding: 0 20px;
  font-weight: bold;
}
.search {
  padding: 4px 12px;
  color: rgba(0,0,0,.70);
  border: 1px solid rgba(0,0,0,.12);
  transition: .15s all ease-in-out;
  background: white;
}
.collection {
  border: 0;
}
.collection-item{
  border: 0;
}

.userList {
  width: 70%;
  margin: 30px auto;
  padding: 30px 60px;
}
</style>
