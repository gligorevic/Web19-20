<template>
  <div class="container center">
    <h2 style="padding-top: 20px; margin-top: 0px;">Amenities</h2>
    <hr />
    <ul class="collection">
      <li
        v-for="amenity in amenities"
        :key="amenity.id"
        class="collection-item"
        style="position: relative;"
      >
        <span>{{amenity.name}}</span>
        <span style="position: absolute; top: 7px; right: 10px;">
          <span class="iconStyle yellowc" @click="showEdit(amenity.id)">
            <i class="material-icons">edit</i>
          </span>
          <span class="iconStyle redc" @click="deleteAmenity(amenity.id)">
            <i class="material-icons">delete</i>
          </span>
        </span>
      </li>
      <li class="collection-item addNewAmenity" @click="showAddPopup = true">
        <i class="small material-icons plus">add</i>
      </li>
    </ul>

    <app-add-dialog v-if="showAddPopup" :closeDialog="closeDialog" :addedAmenity="addedAmenity"></app-add-dialog>
    <app-edit-dialog
      v-if="showEditPopup"
      :closeDialog="closeDialog"
      :editedAmenity="editedAmenity"
      :amenityId="editId"
    ></app-edit-dialog>
  </div>
</template>

<script>
import Axios from "axios";
import AddDialog from "./AddDialog";
import EditDialog from "./EditDialog";
import { eventBus } from "../../main";

export default {
  data() {
    return {
      amenities: [],
      showAddPopup: false,
      showEditPopup: false,
      editId: null,
    };
  },
  components: {
    AppAddDialog: AddDialog,
    AppEditDialog: EditDialog,
  },
  methods: {
    showEdit(id) {
      this.showEditPopup = true;
      this.editId = id;
    },
    closeDialog() {
      this.showAddPopup = false;
      this.showEditPopup = false;
    },
    addedAmenity(a) {
      this.amenities.push(a);
      this.showAddPopup = false;
    },
    editedAmenity(amenity) {
      this.amenities = this.amenities.map((a) =>
        a.id !== amenity.id ? a : amenity
      );
      this.showEditPopup = false;
    },
    async deleteAmenity(id) {
      try {
        await Axios.delete(`/api/amenity/${id}`);
        this.amenities = this.amenities.filter((a) => a.id != id);
        eventBus.showMessage({
          message: "You have been successfully deleted amenity!",
          type: "success",
        });
      } catch (err) {
        console.log(err);
      }
    },
  },
  async beforeCreate() {
    try {
      const res = await Axios.get("/api/amenity");
      console.log(res);
      this.amenities = res.data;
    } catch (err) {
      console.log(err);
    }
  },
};
</script>

<style scoped>
.addNewAmenity {
  background: rgba(133, 133, 133, 0.521);
  padding-bottom: 3px;
  transition: all 0.2s ease-in;
  cursor: pointer;
}

.addNewAmenity:hover {
  background: rgba(133, 133, 133, 0.199);
}

.addNewAmenity:hover .plus {
  color: green;
}

.iconStyle {
  transition: all 0.3s ease;
  cursor: pointer;
  margin-left: 8px;
  opacity: 0.7;
}

.iconStyle:hover {
  transform: scale(1.3);
  opacity: 1;
}

.iconStyle.redc:hover {
  color: rgba(226, 42, 42, 0.829);
}

.iconStyle.yellowc:hover {
  color: rgba(224, 204, 20, 0.884);
}
</style>