<template>
    <div>
      <!-- Booking List Table -->
      <table v-if="$store.state.user.role == 'ROLE_ADMIN'">
        <thead>
          <tr>
            <th>Booking ID</th>
            <th>User ID</th>
            <th>Service ID</th>
            <th>Appointment Date</th>
            <th>Appointment Start Time</th>
            <th>Appointment End Time</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="booking in bookings" v-bind:key="booking.bookingId">
            <td>{{ booking.bookingId }}</td>
            <td>{{ booking.userId }}</td>
            <td>{{ booking.serviceId }}</td>
            <td>{{ booking.appointmentDate }}</td>
            <td>{{ booking.appointmentStartTime }}</td>
            <td>{{ booking.appointmentEndTime }}</td>
            <td>
              <div v-if="booking.isEditing" class="flex-container">
                <input v-model="booking.serviceId" placeholder="Service ID" class="flex-item" />
                <input v-model="booking.appointmentDate" placeholder="Appointment Date" class="flex-item" />
                <input v-model="booking.appointmentStartTime" placeholder="Appointment Start Time" class="flex-item" />
                <input v-model="booking.appointmentEndTime" placeholder="Appointment End Time" class="flex-item" />
              </div>
              <div v-else>
                
                <!-- <router-link
                  v-bind:to="{ name: 'booking-detail', params: { bookingId: booking.bookingId } }"
                >
                  {{ booking }}
                </router-link> -->
              </div>
            </td>
            <td>
              <button
                v-if="!booking.isEditing || $store.state.user.role == 'ROLE_ADMIN'"
                v-on:click="enableEdit(booking)"
                title="Edit"
              >
                <i class="fas fa-edit"></i>
                Edit
              </button>
              <button v-if="booking.isEditing" v-on:click="saveEdit(booking)">
                Save
              </button>
              <button v-if="booking.isEditing" v-on:click="cancelEdit(booking)">
                Cancel
              </button>
              <button v-if="$store.state.user.role == 'ROLE_ADMIN'" v-on:click="deleteBooking(booking)" title="Delete">
                <i class="fas fa-trash"></i>
                Delete
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <h1 v-if="$store.state.user.role == 'ROLE_USER' || $store.state.user.role == 'ROLE_ADMIN'">Create Booking</h1>  
      <!-- New Booking Form -->
      <form id="bookingForm" v-if="$store.state.user.role == 'ROLE_USER' || $store.state.user.role == 'ROLE_ADMIN'" v-on:submit.prevent="addBooking">    
        <div class="flex-container">     
          <label for="service">Service ID: </label>   
          <input class="flex-item"
            type="text"
            v-model="newBooking.serviceId"
            id="service"
            placeholder="Service ID"
            required
            autocomplete="off"
          />
        
          <label for="service">Appointment Date: </label>   
          <input class="flex-item"
            type="text"
            v-model="newBooking.appointmentDate"
            id="date"
            placeholder="YYYY-MM-DD"
            required
            autocomplete="off"
          />  
          
          <label for="service">Appointment Start Time: </label>   
          <input class="flex-item"
            type="text"
            v-model="newBooking.appointmentStartTime"
            id="startTime"
            placeholder="Ex: 14:00:00"
            required
            autocomplete="off"
          />  
         <input type="submit" value="Add Booking" class="flex-item btn btn-primary btn-sm"/>
       
         </div>
      </form>
  
    </div>
</template>
  
<script>
    import bookingService from '../services/BookingService.js';
    
    export default {
    name: 'BookingsList',
    data() {
        return {       
        bookingList: {},                      
        newBooking: {
            bookingId: 0,
            userId: 0,
            serviceId: 0,
            appointmentDate: '',
            appointmentStartTime: '',
            appointmentEndTime: ''
        },
        };
    },
    
    computed:{       
        bookings() {
            return this.$store.state.bookings;
        }
    },
    methods: {
        addBooking() {
        const booking = {
            // userId: this.newBooking.userId,
            serviceId: this.newBooking.serviceId,
            appointmentDate: new Date(this.newBooking.appointmentDate).toISOString(),
            appointmentStartTime: this.newBooking.appointmentStartTime
            // appointmentEndTime: this.newBooking.appointmentEndTime,
            // isEditing: false,
        };
        //Use service to create new booking.
        bookingService.createBooking(booking)
            .then(response => {
            if (response.status === 200 || response.status === 201) {
                // Get bookings and set bookings
                this.getAllBookings();
                alert('Successfully Made Booking, See You Soon!');
                // Reset form
                this.resetForm();
            }
            })
            .catch(error => {
            if (error.response) {
                alert(`Error adding new booking. Response received was "${error.response.statusText}".`, error);
            } else if (error.request) {                
                alert('Error adding new booking. Server could not be reached.', error);
            } else {
                alert('Error adding new booking(Might still be a success, Phone Validation API use limit has been reached). Request could not be created.', error);
            }
            }
            
            );
        },
        enableEdit(booking) {
        // Save original values to allow canceling the edit
            booking.originalUserId = booking.userId;
            booking.originalServiceId = booking.serviceId;
            booking.originalAppointmentDate = booking.appointmentDate;
            booking.originalAppointmentStartTime = booking.appointmentStartTime;
            booking.originalAppointmentEndTime = booking.appointmentEndTime;
            booking.isEditing = true;
        },
        saveEdit(booking) {
          bookingService.updateBooking(booking.bookingId, booking)
            .then(response => {
                if (response.status === 200 || response.status === 201) {
                    // Get bookings and set bookings
                    this.getAllBookings();
                }
            })
            .catch(error => {
                if (error.response) {
                    console.error(`Error updating booking. Response received was "${error.response.statusText}".`, error);
                } else if (error.request) {                
                    console.error('Error updating bookings. Server could not be reached.', error);
                } else {
                    console.error('Error updating booking. Request could not be created.', error);
                }
            })
            .finally(() => {
                booking.isEditing = false;
                delete booking.originalUserId;
                delete booking.originalServiceId;
                delete booking.booking.originalAppointmentDate;
                delete booking.booking.originalAppointmentStartTime;
                delete booking.booking.originalAppointmentEndTime;
            });
        },

        cancelEdit(booking) {
        // Revert to original values and exit edit mode
            booking.originalUserId = booking.userId;
            booking.originalServiceId = booking.serviceId;
            booking.originalAppointmentDate = booking.appointmentDate;
            booking.originalAppointmentStartTime = booking.appointmentStartTime;
            booking.originalAppointmentEndTime = booking.appointmentEndTime;
            booking.isEditing = false;
            delete booking.originalUserId;
            delete booking.originalServiceId;
            delete booking.booking.originalAppointmentDate;
            delete booking.booking.originalAppointmentStartTime;
            delete booking.booking.originalAppointmentEndTime;
        },

        deleteBooking(booking) {
          if (confirm(`Are you sure you want to delete booking: ${booking.bookingId}?`)) {
            bookingService.deleteBooking(booking.bookingId)
            .then(response => {
                if (response.status === 200 || response.status === 201 || response.status === 204) {
                // Get bookings and set bookings                 
                this.getAllBookings();
                }
            })
            .catch(error => {
                if (error.response) {
                console.error(`Error deleting booking. Response received was "${error.response.statusText}".`, error);
                } else if (error.request) {                
                console.error('Error deleting booking. Server could not be reached.', error);
                } else {
                console.error('Error deleting booking. Request could not be created.', error);
                }
            });
        }
        },
        getBookings() {
          bookingService.getAllBookings()
            .then(response => {
            this.bookingList = response.data;
            this.setBookings(this.bookingList);
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
        setBookings(bookings) {
            this.$store.commit('SET_BOOKINGS', bookings);
        },
        resetForm() {
            this.newBooking.originalServiceId = 0;
            this.newBooking.originalAppointmentDate = '';
            this.newBooking.originalAppointmentStartTime = '';
            this.newBooking.originalAppointmentEndTime = '';
        },
    },
    };
</script>


<style scoped>
table {
    margin: auto;
    width: 90vw;
    }

    th,
    td {
    text-align: left;
    padding: 10px;
    vertical-align: top;
    }

    tr:nth-child(even) {
    background-color: rgb(238, 238, 238);
    }

    button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
    }

    button:hover {
    opacity: 0.7;
    }

    form {
    margin-right: 20px;
    width: 90vw;
    }
    h1 {
    margin-bottom: 20px;
    margin-left: 20px;
    }

    form div {
    display: flex;
    margin: auto;
    flex-direction: row;
    flex-wrap: wrap;
    }

    form label {
    margin-right: 10px;
    }
    input{
    margin-right: 5px;
    }
    textarea{
    margin-right: 5px;
    resize:none;
    height: 40px;
    }
    textarea.placeholder{
    text-align: left;
    vertical-align: middle;
    }

    .flex-container{
    display: flex;
    align-content: flex-start;
    flex-direction: row;
    flex-wrap: wrap;
    }
    .flex-item {
        /* Sets flex-grow, flex-shrink, and flex-basis */ 
    flex: 1 1 1;               
    text-align: center;
    }
  @media only screen and (min-width: 1024px) { 
    table {
    margin: auto;
    width: 90vw;
    }

    th,
    td {
    text-align: left;
    padding: 10px;
    vertical-align: top;
    }

    tr:nth-child(even) {
    background-color: rgb(238, 238, 238);
    }

    button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
    }

    button:hover {
    opacity: 0.7;
    }

    form {
    margin-right: 20px;
    width: 90vw;
    }
    h1 {
    margin-bottom: 20px;
    margin-left: 20px;
    }

    form div {
    display: flex;
    margin: auto;
    flex-direction: row;
    flex-wrap: wrap;
    }

    form label {
    margin-right: 10px;
    }
    input{
    margin-right: 5px;
    }
    textarea{
    margin-right: 5px;
    resize:none;
    height: 40px;
    }
    textarea.placeholder{
    text-align: left;
    vertical-align: middle;
    }

    .flex-container{
    display: flex;
    align-content: flex-start;
    flex-direction: row;
    flex-wrap: wrap;
    }
    .flex-item {
        /* Sets flex-grow, flex-shrink, and flex-basis */ 
    flex: 1 1 1;               
    text-align: center;
    }
  }

  @media only screen and (max-width: 425px) { 
    table {
    margin: auto;
    width: 90vw;
    }

    th,
    td {
    text-align: left;
    padding: 10px;
    vertical-align: top;
    }

    tr:nth-child(even) {
    background-color: rgb(238, 238, 238);
    }

    button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
    }

    button:hover {
    opacity: 0.7;
    }

    form {
    margin-right: 20px;
    width: 90vw;
    }
    h1 {
    margin-bottom: 20px;
    margin-left: 20px;
    }

    form div {
    display: flex;
    margin: auto;
    flex-direction: row;
    flex-wrap: wrap;
    }

    form label {
    margin-right: 10px;
    }
    input{
    margin-right: 5px;
    }
    textarea{
    margin-right: 5px;
    resize:none;
    height: 40px;
    }
    textarea.placeholder{
    text-align: left;
    vertical-align: middle;
    }

    .flex-container{
    display: flex;
    align-content: flex-start;
    flex-direction: row;
    flex-wrap: wrap;
    }
    .flex-item {
        /* Sets flex-grow, flex-shrink, and flex-basis */ 
    flex: 1 1 1;               
    text-align: center;
    }
  }

</style>
  