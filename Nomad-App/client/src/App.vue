<template>
  <div id="cart-app">
    <header>
      <site-header />
    </header>

    <nav>
        <router-link v-bind:to="{ name: 'home' }" v-if="$store.state.token">
          Home
        </router-link>
        <router-link v-bind:to="{ name: 'booking-availability' }" v-if="$store.state.token">
          My Availability
        </router-link>
        <router-link v-bind:to="{ name: 'services' }" v-if="$store.state.token">
          Services
        </router-link>
        <router-link v-bind:to="{ name: 'clients' }" v-if="$store.state.token">
          Clients
        </router-link>
        <router-link v-bind:to="{ name: 'bookings' }" v-if="$store.state.token">
          Bookings
        </router-link>
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token">
          Logout
        </router-link>
        <router-link v-bind:to="{ name: 'login' }" v-else>
          Login
        </router-link>
    </nav>
    <main>
      <router-view />
    </main>
    <site-footer />
  </div>
</template>

<script>
import SiteHeader from './components/SiteHeader.vue';
import SiteFooter from './components/SiteFooter.vue';

export default {
    components:{
      SiteHeader,
      SiteFooter
    }
  };

</script>

<style scoped>
  .main {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    max-width: 100vw;
    /* margin: 10px auto 0 auto; */
  }

  #cart-app {
    display: grid;
    grid-template-areas:
      "header"
      "nav"
      "main"
      "footer";
    grid-template-columns: 1fr;
    /* grid-template-rows: auto auto 1fr auto; */
    min-height: 98vh;
    justify-items: center;

  }

  /* Style the grid areas */
  header {
    grid-area: header;
    background-color: #f8f9fa;
    /* width: 100vw; */
    /* justify-content: center; */
    /* padding: 1rem; */
  }

  nav {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    grid-area: nav;
    background-color: #e9ecef;
    /* padding: 1rem; */
    width:98vw;
  } 

  main {
    grid-area: main;
    padding: 1rem;
  }

  footer {
    grid-area: footer;
    background-color: #f8f9fa;
    /* padding: 1rem; */
    text-align: center;
  }
</style>