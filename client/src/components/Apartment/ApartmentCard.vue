<template>
  <div>
    <img
      :src="apartment.images && 'http://localhost:8081/api/' + apartment.images[0]"
      class="coverImg"
    />
    <div class="apartmentInfo">
      <h5>{{ apartment.name }}</h5>
    </div>
  </div>
</template>
<script>
export default {
  props: ["apartment"],
  created() {
    console.log(this.apartment);
  },
};
</script>
<style scoped>
.coverImg {
  max-width: 100%;
  opacity: 0.8;
  transition: all 0.2s ease-in;
  cursor: pointer;
}

.coverImg:hover {
  opacity: 1;
}

.apartmentInfo {
  padding: 2px 10px;
}
</style>