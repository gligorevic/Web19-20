<template>
  <div class="container row">
    <div class="col s12 element" style="margin-top: 25px; height: 250px;">
      <app-search></app-search>
    </div>
    <div class="col s3 element" style="margin-top: 15px; height: 550px;">
      <app-filter></app-filter>
    </div>
    <div class="col s9" style="margin-top: 5px;">
      <app-apartments></app-apartments>
    </div>
  </div>
</template>
<script>
import Search from "./Home/Search";
import Filter from "./Home/Filter";
import Apartments from "./Home/Apartments";

export default {
  components: {
    AppSearch: Search,
    AppFilter: Filter,
    AppApartments: Apartments,
  },
};
</script>

<style scoped>
.element {
  box-shadow: -1px 3px 6px -1px rgba(0, 0, 0, 0.75);
}
</style>
