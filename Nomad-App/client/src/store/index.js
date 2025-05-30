import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      nextBookingId: 40,
      nextServiceId: 15,
      nextClientId: 12,
      bookings: [
        {
          bookingId: 0,
          userId: 0,
          serviceId: 0,
          appointmentDate: '',
          appointmentStartTime: '',
          appointmentEndTime: ''
        }
      ],
      services: [
        {
          serviceId: 0,
          serviceName: '',
          description: '',
          cost: 0,
          lengthMinutes: 0
        }
      ],
      clients: [
        {
          clientId: 0,
          userId: 0,
          firstName: '',
          lastName: '',
          phoneNumber: ''
        }
      ]
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
      SET_BOOKINGS(state, bookings) {
        state.bookings = bookings;
      },
      //TODO fix
      ADD_BOOKING(state, booking) {
        const myBooking = state.bookings.find(b => b.bookingId == booking.bookingId);
        booking.bookingId = state.nextBookingId++;
        this.bookings.push(myBooking);
      },
      SET_SERVICES(state, services) {
        state.services = services;
      },
      //TODO fix
      ADD_SERVICE(state, service) {
        const myService = state.services.find(s => s.serviceId == service.serviceId);
        service.serviceId = state.nextServiceId++;
        this.services.push(myService);
      },
      SET_CLIENTS(state, clients) {
        state.clients = clients;
      },
      //TODO fix
      ADD_CLIENT(state, client) {
        const myClient = state.services.find(c => c.clientId == client.clientId);
        client.clientId = state.nextClientId++;
        this.clients.push(myClient);
      },
    },

  })
  return store;
}