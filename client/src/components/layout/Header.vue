<template>
  <nav>
    <div class="nav-wrapper">
      <a href="#" class="brand-logo" style="display: flex"
        ><i class="material-icons">card_travel</i>
        <span>Apartments</span>
      </a>
      <ul id="nav-mobile" class="right hide-on-med-and-down">
        <li><a href="sass.html">Home</a></li>
        <li v-if="isUserLoggedIn"><a href="badges.html">Profile</a></li>
        <li v-if="isUserLoggedIn"><a href="collapsible.html">Logout</a></li>
        <li v-else><a href="collapsible.html">Login</a></li>
      </ul>
    </div>
  </nav>
</template>

<script>
import { eventBus } from "../../main";
export default {
  data: () => ({
    isUserLoggedIn: eventBus.isUserLoggedIn,
  }),
};
</script>
