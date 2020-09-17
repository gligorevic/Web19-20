<template>
  <div class="container">
    <h3 align="center">User reviews</h3>
    <div class="row">
      <div class="col s12" v-for="comment in comments" :key="comment.id">
        <div class="card">
          <div class="commentHeader">
            <span class="card-title">
              <b>{{comment.guest.username}}</b>
            </span>
            <div class="switch" v-if="currentUser.role === 'HOST' && currentUser.id == hostId">
              <label>
                <input
                  type="checkbox"
                  @input="changeActive(comment)"
                  :checked="comment.showComment"
                />
                <span class="lever"></span>
              </label>
            </div>
          </div>
          <div>
            <div class="rating">
              <span>Rating:</span>
              <span v-for="n in comment.grade" :key="n">
                <i class="material-icons">star</i>
              </span>
            </div>
            <div class="commentText">
              <span>
                Text:
                <em>{{comment.text}}</em>
              </span>
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
  props: ["comments", "hostId"],
  data() {
    return {
      currentUser: eventBus.currentUser,
    };
  },
  methods: {
    async changeActive(comment) {
      try {
        await Axios.put("/api/comments/" + comment.id + "/status");
        eventBus.showMessage({
          message: "You have successfully changed comment status!",
          type: "success",
        });
      } catch (err) {
        console.log(err.response.data);
      }
    },
  },

  computed: {
    getHostId() {
      return this.hostId;
    },
  },
};
</script>
  

<style scoped>
.rating {
  display: flex;
  align-items: center;
  border-bottom: 1px dashed #3131313f;
  padding: 5px 10px;
}

.commentHeader {
  padding: 5px 10px;
  display: flex;
  justify-content: space-between;
  background: #b1d7f7;
}

.commentText {
  padding: 5px 10px;
}

.switch label input[type="checkbox"]:checked + .lever {
  background-color: #64b5f6 !important;
}

.switch label input[type="checkbox"]:checked + .lever:after {
  background-color: #64b5f6 !important;
}
</style>