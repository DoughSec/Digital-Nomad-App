<template>
    <h1 v-if="$store.state.user.role == 'ROLE_ADMIN'">Clients</h1>
    <clients-list />
     <!-- <div v-for="service in services" v-bind:key="service.serviceId">
        {{ service.serviceName }}
     </div> -->
  </template>
  
  <script>
  import ClientsList from "../components/ClientsList.vue";
  import clientService from '../services/ClientService.js';
  
  export default {
    data(){
      return{
        clients:[]
      }
    },
    components: {
      ClientsList
    },
    //LOOK HERE: change role to ROLE_USER in the register view TO CHECK OUT THE USER VIEWS
        // ***The Login for the default admin role is this: username: ADMIN, password: PASSWORD1227
    methods:{
        getClients() {
            clientService.getAllClients() 
                .then(response => {
                    this.clients = response.data;
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
        setClients(clients) {
            this.$store.commit('SET_CLIENTS', clients);
        },
    },
    created() {
        clientService.getAllClients()
        .then(response => {
            this.clients = response.data.map(client => ({
            ...client,
            isEditing: false,
            }));
                                    
            this.setClients(this.clients);
        })
            .catch(error => {
            if (error.response) {
                console.error(`Error getting clients. Response received was "${error.response.statusText}".`, error);
            } else if (error.request) {                
                console.error('Error getting clients. Server could not be reached.', error);
            } else {
                console.error('Error getting clients. Request could not be created.', error);
            }
            
            });
    },
  };
  </script>
  