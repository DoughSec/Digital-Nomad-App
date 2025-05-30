<template>
    <h1>Services</h1>
    <services-list />
     <!-- <div v-for="service in services" v-bind:key="service.serviceId">
        {{ service.serviceName }}
     </div> -->
  </template>
  
  <script>
  import ServicesList from "../components/ServicesList.vue";
  import servicesService from '../services/ServicesService.js';
  
  export default {
    data(){
      return{
        services:[]
      }
    },
    components: {
      ServicesList
    },
    //LOOK HERE: change role to ROLE_USER in the register view TO CHECK OUT THE USER VIEWS
    // ***The Login for the default admin role is this: username: ADMIN, password: PASSWORD1227
    methods:{
        getServices() {
            servicesService.getAllServices() 
                .then(response => {
                    this.services = response.data;
                })
                .catch(error => {
                    this.handleErrorResponse(error);
                })
        },
        handleErrorResponse(error) {
            if (error.response.status == 404) {
                alert(error);
            } else {
                // this.isLoading = false;
                alert(error);
            }
        },
        setServices(services) {
            this.$store.commit('SET_SERVICES', services);
        },
    },
    created() {
        servicesService.getAllServices()
        .then(response => {
            this.services = response.data.map(service => ({
            ...service,
            isEditing: false,
            }));
                                    
            this.setServices(this.services);
        })
            .catch(error => {
            if (error.response) {
                console.error(`Error getting services. Response received was "${error.response.statusText}".`, error);
            } else if (error.request) {                
                console.error('Error getting services. Server could not be reached.', error);
            } else {
                console.error('Error getting services. Request could not be created.', error);
            }
            
            });
    },
  };
  </script>
  