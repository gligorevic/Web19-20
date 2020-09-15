<template>
  <div class="col s4">
    <div class="wraper">
      <div class="cardToolbar">
        <div class="price">
          {{apartment.pricePerNight}}€
          <sub>/night</sub>
        </div>
      </div>
      <app-apartment-card :apartment="apartment"></app-apartment-card>
      <div style="display: flex; flex-direction: row-reverse; padding: 10px;">
        <router-link
          :to="'/apartment/' + apartment.id + '/res'"
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
import ApartmentCard from "../ApartmentCard";
export default {
  props: ["apartment"],
  components: {
    AppApartmentCard: ApartmentCard,
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

.price {
  padding: 2px 7px;
  border-radius: 15px;
  background: #64b5f6;
  text-align: center;
  opacity: 0.9;
  transition: all 0.2s ease-in;
}
.price:hover {
  opacity: 1;
  transform: scale(1.03);
}
</style>