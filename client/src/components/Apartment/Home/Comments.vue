<template>
  <div class = "container">
    <h3 align="center">User reviews</h3>
      <div class="row">
          <div class="col s12" v-for="comment in comments" :key="comment.id">
            <div class="card teal lighten-2">
              <div class="card-content white-text">
                <div class="switch" style="flex-grow: 1;" v-if="currentUser.role === 'HOST'">
                  <label>
                    <input type="checkbox" @input="changeActive(comment)" :checked="comment.showComment" />
                    <span class="lever"></span>
                  </label>
                </div>
                <span class="card-title"><b>{{comment.guest.username}}</b></span>
                  <div class="row">
                    <span>Rating:</span>
                    <span v-for="n in comment.grade" :key="n">
                     <i class="material-icons">star</i></span>
                  </div> 
                  <div class="row">
                    <span>{{comment.text}}</span>
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
  props: ["comments"],
  data() {
    return { 
    currentUser: eventBus.currentUser,
    } 
  },
  methods: {
    async changeActive(comment) {
      try {
          await Axios.put(
            "/api/comments/" + comment.id + "/status"
          );
          eventBus.showMessage({
          message: "You have successfully changed comment status!",
          type: "success",
        });
       
      } catch (err) {
        console.log(err.response.data);
      }
    },
  },
};
</script>
  

<style scoped>
.switch label input[type="checkbox"]:checked + .lever {
  background-color: #64b5f6 !important;
}

.switch label input[type="checkbox"]:checked + .lever:after {
  background-color: #64b5f6 !important;
}
</style>