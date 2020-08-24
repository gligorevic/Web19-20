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
          <input type="text" v-model="search" placeholder="Search username.."/> 
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
import AddHost from './AddHost.vue'
import Axios from "axios";
export default {
  data() {
    return {
      showAddHostPopup: false,
      users: [],
      search:''
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
      this.userData = { ...this.users, ...newUserData };
      this.closeDialog();
    },
  },
  computed: {
    filteredUsers: function(){
      return this.users.filter((user) => {
        return user.username.toLowerCase().match(this.search.toLowerCase());
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
input {
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
  margin: 70px auto;
  padding: 30px 60px;
}
</style>