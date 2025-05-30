import axios from 'axios';

const servicesService = {
    getAllServices() {  
      return axios.get('/services');
    },
    getServiceById(serviceId) {
      return axios.get(`/services/${serviceId}`);
    },
    createService(service) {
      return axios.post('/services', service);
    },
    updateService(serviceId, service) {
      return axios.put(`/services/${serviceId}`, service);
    }
  };
  
  export default servicesService;