<template>
  <div class="col s4">
    <div class="wraper">
      <div class="cardToolbar">
        <span class="iconStyle redc">
          <i class="material-icons">delete</i>
        </span>
        <span class="iconStyle yellowc">
          <i class="material-icons">edit</i>
        </span>
        <div class="switch" style="flex-grow: 1;">
          <label>
            <input type="checkbox" @input="changeActive" :checked="status == 'ACTIVE'" />
            <span class="lever"></span>
          </label>
        </div>
      </div>
      <app-apartment-card :apartment="apartment"></app-apartment-card>
      <div style="display: flex; flex-direction: row-reverse; padding: 10px;">
        <router-link
          :to="'/apartment/' + apartment.id"
          tag="a"
          exact
          style="display: inline-block; padding: 0px 7px;"
          class="waves-effect waves btn blue lighten-2"
        >View more</router-link>
      </div>
    </div>
  </div>
</template>
<script>
import ApartmentCard from "./ApartmentCard";
import Axios from "axios";

export default {
  props: ["apartment"],
  data() {
    return {
      status: this.apartment.status,
    };
  },
  components: {
    AppApartmentCard: ApartmentCard,
  },
  methods: {
    async changeActive(e) {
      console.log(e)
      try {
        if (e.target.checked) {
          await Axios.put(
            "/api/apartment/" + this.apartment.id + "/status",
            "ACTIVE"
          );
        } else {
          await Axios.put(
            "/api/apartment/" + this.apartment.id + "/status",
            "INACTIVE"
          );
        }
      } catch (err) {
        console.log(err.response.data);
      }
    },
  },
};
</script>
<style scoped>
.col {
  padding: 0;
}

.wraper {
  box-shadow: -1px 2px 4px -1px rgba(0, 0, 0, 0.75);
  margin: 10px;
}

.cardToolbar {
  border-bottom: 1px solid #66626265;
  display: flex;
  flex-direction: row-reverse;
  padding: 5px 10px;
  padding-left: 0;
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

.switch label input[type="checkbox"]:checked + .lever {
  background-color: #64b5f6 !important;
}

.switch label input[type="checkbox"]:checked + .lever:after {
  background-color: #64b5f6 !important;
}
</style>