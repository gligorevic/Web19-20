<template>
  <div class="container row">
    <div class="col s12 element" style="margin-top: 25px; ">
      <app-search :submitSearch="submitSearch"></app-search>
    </div>
    <div class="col s3 element" style="margin-top: 15px; padding-bottom: 20px;">
      <span class="sortBy" @click="sortByPrice" :class="{greenBack: sort !== 'none'}">
        <i class="material-icons">sort</i>
        <span style="margin-left: 3px;">Sort by price{{sort != "none" ? ": " + sort : ""}}</span>
      </span>
      <app-filter :filterByTypes="filterByTypes" :filterByAmenities="filterByAmenities"></app-filter>
    </div>
    <div class="col s9" style="margin-top: 5px;">
      <app-apartments :apartments="apartments"></app-apartments>
    </div>
  </div>
</template>
<script>
import Axios from "axios";
import Search from "./Home/Search";
import Filter from "./Home/Filter";
import Apartments from "./Home/Apartments";

export default {
  data() {
    return {
      allApartments: [],
      apartments: [],
      typesFilter: [],
      amenitiesFilter: [],
      sort: "none",
    };
  },
  components: {
    AppSearch: Search,
    AppFilter: Filter,
    AppApartments: Apartments,
  },
  methods: {
    async submitSearch(searchData) {
      try {
        const res = await Axios.post("/api/apartment/search", searchData);
        this.allApartments = res.data;
        this.apartments = res.data;
        this.filterApartments();
        console.log(res);
      } catch (error) {
        console.log(error);
      }
    },
    sortByPrice() {
      this.sort !== "asc" ? (this.sort = "asc") : (this.sort = "desc");
      if (this.sort === "asc") {
        this.apartments.sort((a, b) => a.pricePerNight - b.pricePerNight);
        this.allApartments.sort((a, b) => a.pricePerNight - b.pricePerNight);
      } else {
        this.apartments.sort((a, b) => b.pricePerNight - a.pricePerNight);
        this.allApartments.sort((a, b) => b.pricePerNight - a.pricePerNight);
      }
    },
    filterApartments() {
      this.filterOnlyByAmenities();

      if (this.typesFilter.length > 0) {
        this.apartments = this.apartments.filter((a) =>
          this.typesFilter.every((aF) => a.type == aF)
        );
      } else {
        this.apartments = this.AllApartments;
        this.filterOnlyByAmenities();
      }
    },
    filterOnlyByAmenities() {
      if (this.amenitiesFilter.length > 0) {
        this.apartments = this.allApartments.filter((a) =>
          this.amenitiesFilter.every(
            (aF) =>
              a.amenities.filter((amenity) => aF === amenity.name).length > 0
          )
        );
      } else {
        this.apartments = this.allApartments;
      }
    },
    filterByTypes(types) {
      this.typesFilter = types;
      this.filterApartments();
    },
    filterByAmenities(amenities) {
      this.amenitiesFilter = amenities;
      this.filterApartments();
    },
  },
  async beforeCreate() {
    try {
      const res = await Axios.get("/api/apartment");
      this.allApartments = res.data;
      this.apartments = res.data;
    } catch (err) {
      console.log(err);
    }
  },
};
</script>

<style scoped>
.element {
  box-shadow: -1px 3px 6px -1px rgba(0, 0, 0, 0.75);
}
.sortBy {
  cursor: pointer;
  color: gray;
  margin: 5px;
  padding: 5px;
  display: inline-block;
  transition: all 0.2s ease-in;
  border-radius: 50px;
  display: flex;
  align-items: center;
  margin-bottom: 0;
}

.greenBack {
  background: rgb(224, 245, 224);
}

.sortBy:hover {
  color: rgb(80, 80, 80);
  background: rgb(226, 235, 238);
}
</style>
