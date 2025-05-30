<template>
    <h1 v-if="$store.state.user.role == 'ROLE_ADMIN'">Bookings</h1>
    <h1>
        ***Must register as a client under the "Clients" tab 
        before making a booking to verify phoneNumber***
    </h1>
    <h1>
        P.S: Refer to "Services" tab for the Service ID
    </h1>
    <BookingsList />
     <!-- <div v-for="booking in bookings" v-bind:key="booking.bookingId">
        {{ booking.appointmentDate }}
     </div> -->
  </template>
  
  <script>
  import BookingsList from "../components/BookingsList.vue";
  import bookingService from '../services/BookingService.js';
  
  export default {
    data(){
      return{
        bookings:{}
      }
    },
    components: {
      BookingsList
    },
    //LOOK HERE: change role to ROLE_USER in the register view TO CHECK OUT THE USER VIEWS
        // ***The Login for the default admin role is this: username: ADMIN, password: PASSWORD1227
    methods:{
        getBookings() {
            bookingService.getAllBookings() 
                .then(response => {
                    this.bookings = response.data;
                })
                .catch(error => {
                    this.handleErrorResponse(error);
                })
        },
        handleErrorResponse(error) {
            if (error.response.status == 404) {
                this.$router.push({name: 'NotFoundView'});
            } else {
                this.isLoading = false;
                this.$store.commit('SET_NOTIFICATION', `Could not get topic data from server.`);
            }
        },
        setBookings(bookings) {
            this.$store.commit('SET_BOOKINGS', bookings);
        },
    },
    created() {
        bookingService.getAllBookings()
        .then(response => {
            this.bookings = response.data.map(booking => ({
            ...booking,
            isEditing: false,
            }));
                                    
            this.setBookings(this.bookings);
        })
            .catch(error => {
            if (error.response) {
                console.error(`Error getting bookings. Response received was "${error.response.statusText}".`, error);
            } else if (error.request) {                
                console.error('Error getting bookings. Server could not be reached.', error);
            } else {
                console.error('Error getting bookings. Request could not be created.', error);
            }
            
            });
    },
  };
  </script>
  