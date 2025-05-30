import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeSalonView from '../views/HomeSalonView.vue'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'
// import BookingDetailView from '../views/BookingDetailView.vue'
import BookingsView from '../views/BookingsView.vue'
import ServicesView from '../views/ServicesView.vue'
import ClientsView from '../views/ClientsView.vue'
import AvailabilityView from '../views/AvailabilityView.vue'
// import BookingDetailView from '../views/BookingDetailView.vue'


/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
    //Home Salon View  
    {
      path: '/',
      name: 'home',
      component: HomeSalonView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: LogoutView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView,
      meta: {
        requiresAuth: false
      }
    },
    
    //booking views
    {
      path: "/bookings",
      name: "bookings",
      component: BookingsView
    },
    // {
    //   path: "/bookings/:id",
    //   name: "booking-detail",
    //   component: BookingDetailView
    // },
    // {
    //   path: "/bookings/users",
    //   name: "user-bookings",
    //   component: BookingsView,
    // },

    // {
    //   path: "/bookings/users/addBooking",
    //   name: "add-user-booking",
    //   component: AddBookingView,
    // },

    //Availability View
    {
      path: "/bookings/availability",
      name: "booking-availability",
      component: AvailabilityView,
    },

    //Services View
    {
      path: "/services",
      name: "services",
      component: ServicesView
    },

    //Clients View
    {
      path: "/clients",
      name: "clients",
      component: ClientsView
    },
    
  ];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
