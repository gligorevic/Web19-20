<template>
  <div class="wraper">
    <div class="addForm">
      <form>
        <div @click.prevent="closeDialog" class="closeButton">
          <i class="material-icons">close</i>
        </div>
        <div class="input-field col s6">
          <input id="amenity" type="text" class="validate" v-model="amenity.name" />
          <label for="amenity">Amenity name</label>
        </div>
        <button
          style="display: inline-block; float: right;"
          class="waves-effect waves btn blue lighten-2"
          @click.prevent="addAmenity"
          :disabled="!amenity.name"
        >Add amenity</button>
      </form>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { eventBus } from "../../main";
export default {
  props: ["closeDialog", "addedAmenity"],
  data() {
    return { amenity: { name: "" } };
  },
  beforeCreate() {
    document.querySelector("body").style.overflow = "hidden";
  },

  beforeDestroy() {
    document.querySelector("body").style.overflowY = "scroll";
  },
  methods: {
    async addAmenity() {
      try {
        const res = await Axios.post("/api/amenity", this.amenity);
        console.log(res);
        eventBus.showMessage({
          message: "You have been successfully created new amenity!",
          type: "success",
        });
        this.addedAmenity(res.data);
      } catch (err) {
        eventBus.showMessage({
          message: "Amenity with given name already exists!",
          type: "error",
        });
        console.log(err);
      }
    },
  },
};
</script>

<style scoped>
.wraper {
  background: rgba(128, 128, 128, 0.267);
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100vw;
  height: 100vh;
  text-align: left;
}

.addForm {
  width: 450px;
  position: absolute;
  top: 50%;
  left: 50%;
  background: white;
  transform: translate(-50%, -50%);
  padding: 30px 50px;
}

.closeButton {
  position: absolute;
  top: 10px;
  right: 15px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
}

.closeButton:hover {
  transform: scale(1.1);
}
</style>