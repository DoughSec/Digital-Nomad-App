import axios from 'axios';

const servicesService = {
    getAllUsers() {  
      return axios.get('/users');
    },
    getUserById(userId) {
      return axios.get(`/users/${userId}/id`);
    },
    getUserByUsername(username) {
        return axios.get(`/users/${username}`);
    },
    getUserClient() {
        return axios.get('/user');
    },
    updateUser(user) {
      return axios.put('/user', user);
    }
  };
  
  export default servicesService;