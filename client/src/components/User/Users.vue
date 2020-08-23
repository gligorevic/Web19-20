<template>
  <div class="userList">
    <h3>
      List of registered users
    </h3>   
    <div>
      <div>
        <input type="text" v-model="search" placeholder="Search username.."/> 
      </div>
      <div class="row">
        <div class="col l4" v-for="user in filteredUsers" :key="user.id">
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
</template>

<script>
import Axios from "axios";
export default {
  data() {
    return {
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
  width: 50%;
  margin: 70px auto;
}
</style>