import axios from 'axios';

const clientService = {
    getAllClients() {  
      return axios.get('/clients');
    },
    getClientById(clientId) {
      return axios.get(`/clients/${clientId}`);
    },
    createClient(client) {
      return axios.post('/clients', client);
    },
    updateClient(clientId, client) {
      return axios.put(`/clients/${clientId}`, client);
    }
  };
  
  export default clientService;