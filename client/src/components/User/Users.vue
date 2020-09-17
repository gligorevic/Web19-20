<template>
  <div>
    <div class="userList">
      <div class="row">
        <div class="col s6">
          <h3>
          List of registered users
          </h3>
        </div>
        <div class="col s6">
          <div class="buttonContainer">
            <div class="buttonCenter">
              <button
              padding-top="10px"
              class="btn waves-effect waves-light"
              name="action"
              @click = openAddForm
            >
              Add new host
              <i class="material-icons right">add</i>
            </button>
            </div>
          </div>
        </div>
      </div>   
      <div>
        <div>
          <input class="searchField" type="text" v-model="search" placeholder="Search username.."/> 
        </div>
        <div class="row">
          <div class="col s6">
            <form action="#">
              <p font-size="20">User gender:</p>
              <input type="radio" id="one" value="" v-model="gender">
              <label for="one">Any</label>
              <br>
              <input type="radio" id="two" value="MALE" v-model="gender">
              <label for="two">Male</label>
              <br>
              <input type="radio" id="three" value="FEMALE" v-model="gender">
              <label for="three">Female</label>
            </form>
          </div>
          <div class="col s6">
            <p>User role:</p>
            <form action="#">
              <input type="checkbox" id="Admin" value="ADMIN" v-model="userRole">
              <label for="Admin">Admin</label>
              <br>
              <input type="checkbox" id="Host" value="HOST" v-model="userRole">
              <label for="Host">Host</label>
              <br>
              <input type="checkbox" id="Guest" value="GUEST" v-model="userRole">
              <label for="Guest">Guest</label>
              <br>
            </form>
          </div>
        </div>
        <div class="divider">
        </div>
        <div class="row">
          <div class="col s4" v-for="user in filteredUsers" :key="user.id">
            <div class="card blue-grey darken-1">
              <div class="card-content white-text">
                <span class="card-title">{{user.username}}</span>
                <p>
                  First Name: {{user.name}}
                  <br/>
                  Last Name: {{user.lastName}}
                  <br/>
                  Gender : {{user.gender}}
                  <br/>
                  Role : {{user.role}}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <transition name="slide" type="animation" mode="out-in">
        <app-add-host
          :closeDialog="closeDialog"
          :closeDialogAndUpdateState="closeDialogAndUpdateState"
          v-if="showAddHostPopup"
        ></app-add-host>
      </transition> 
  </div>
</template>

<script>
import AddHost from './AddHost.vue';
import Axios from "axios";
export default {
  data() {
    return {
      showAddHostPopup: false,
      users: [],
      search:'',
      gender:'',
      userRole: [],
    };
  },
  async created() {
    try {
      Axios.get(`/api/user`).then(response => this.users = response.data)
    } catch (err) {
      console.log(err);
    }
  },
  components:{
    AppAddHost: AddHost
  },
  methods: {
    openAddForm() {
      this.showAddHostPopup = true;

      document.querySelector("body").style.overflow = "hidden";
      window.scrollTo(0, 0);
    },
    closeDialog() {
      this.showAddHostPopup = false;
      document.querySelector("body").style.overflow = "scroll";
    },
    closeDialogAndUpdateState(newUserData) {
      this.userData = this.users.push(newUserData)
      this.closeDialog();
    },
  },
  computed: {
    filteredUsers: function(){
      return this.users.filter((user) => {
        if(this.userRole.length > 0){
        return user.username.toLowerCase().match(this.search.toLowerCase()) && user.gender.match(this.gender) && this.userRole.includes(user.role);
        }else{
          return user.username.toLowerCase().match(this.search.toLowerCase()) && user.gender.match(this.gender) 
        }
      })
    }
  }
}
</script>

<style scoped>
.buttonContainer{
  position: relative;
}
.buttonCenter {
  position: absolute;
  right: 0px;
  top: 26px;
}
.searchField {
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