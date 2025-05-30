import axios from 'axios';

const bookingService = {
    getAllBookings() {  
      return axios.get('/bookings');
    },
    getBookingById(bookingId) {
      return axios.get(`/bookings/${bookingId}`);
    },
    getBookingByUser() {
        return axios.get('/bookings/users');
    },
    getBookingAvailability() {  
        return axios.get('/bookings/availability');
    },
    //TODO: fix createBooking
    createBooking(booking) {
      alert(JSON.stringify(booking));
      return axios.post('/bookings/users', booking);
    },
    updateBooking(bookingId, booking) {
      return axios.put(`/bookings/${bookingId}`, booking);
    },
    deleteBooking(bookingId) {
      return axios.delete(`/bookings/${bookingId}`);
    },
  };
  
  export default bookingService;