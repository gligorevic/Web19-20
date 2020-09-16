<template>
  <div class="wraper">
    <div class="addForm">
      <form>
        <div @click.prevent="closeDialog" class="closeButton">
          <i class="material-icons">close</i>
        </div>
        <div class="input-field col s6">
          <input id="amenity" type="text" class="validate" v-model="amenity.name" />
          <label for="amenity" class="active">Amenity name</label>
        </div>
        <button
          style="display: inline-block; float: right;"
          class="waves-effect waves btn blue lighten-2"
          @click.prevent="editAmenity"
          :disabled="!amenity.name"
        >Edit amenity</button>
      </form>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { eventBus } from "../../main";
export default {
  props: ["closeDialog", "editedAmenity", "amenityId"],
  data() {
    return { amenity: { name: "" } };
  },
  async created() {
    try {
      const res = await Axios.get(`/api/amenity/${this.amenityId}`);
      this.amenity = res.data;
    } catch (err) {
      console.log(err);
    }
    document.querySelector("body").style.overflow = "hidden";
  },

  beforeDestroy() {
    document.querySelector("body").style.overflowY = "scroll";
  },
  methods: {
    async editAmenity() {
      try {
        const res = await Axios.put(
          `/api/amenity/${this.amenityId}`,
          this.amenity
        );
        console.log(res);
        eventBus.showMessage({
          message: "You have been successfully edited amenity!",
          type: "success",
        });
        this.editedAmenity(res.data);
      } catch (err) {
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