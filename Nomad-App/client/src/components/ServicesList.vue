<template>
    <div>
      <!-- Service List Table -->
      <table>
        <thead>
          <tr>
            <th>Service ID</th>
            <th>Service Name</th>
            <th>Description</th>
            <th>Cost</th>
            <th>Length in Minutes</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="service in services" v-bind:key="service.serviceId">
            <td>{{ service.serviceId }}</td>
            <td>{{ service.serviceName }}</td>
            <td>{{ service.description }}</td>
            <td>{{ service.cost }}</td>
            <td>{{ service.lengthMinutes }}</td>
            <td>
              <div v-if="service.isEditing" class="flex-container">
                <input v-model="service.serviceName" placeholder="Service Name" class="flex-item" />
                <input v-model="service.description" placeholder="Description" class="flex-item" />
                <input v-model="service.cost" placeholder="Cost" class="flex-item" />
                <input v-model="service.lengthMinutes" placeholder="Length Minutes" class="flex-item" />
              </div>
              <div v-else>
                <!-- <router-link
                  v-bind:to="{ name: 'service-detail', params: { serviceId: service.serviceId } }"
                >
                  {{ service }}
                </router-link> -->
              </div>
            </td>
            <td>
              <button
                v-if="!service.isEditing && $store.state.user.role == 'ROLE_ADMIN'"
                v-on:click="enableEdit(service)"
                title="Edit"
              >
                <i class="fas fa-edit"></i>
                Edit
              </button>
              <button v-if="service.isEditing" v-on:click="saveEdit(service)">
                Save
              </button>
              <button v-if="service.isEditing" v-on:click="cancelEdit(service)">
                Cancel
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <h1 v-if="$store.state.user.role == 'ROLE_ADMIN'">Create Service</h1>  
      <!-- New Service Form -->
      <form id="serviceForm" v-if="$store.state.user.role == 'ROLE_ADMIN'" v-on:submit.prevent="addService">    
        <div class="flex-container">     
          <label for="service">Service Name: </label>   
          <input class="flex-item"
            type="text"
            v-model="newService.serviceName"
            id="service"
            placeholder="Service Name"
            required
            autocomplete="off"
          />
        
          <label for="service">Description: </label>   
          <input class="flex-item"
            type="text"
            v-model="newService.description"
            id="date"
            placeholder="Description"
            required
            autocomplete="off"
          />  
          
          <label for="service">Cost: </label>   
          <input class="flex-item"
            type="text"
            v-model="newService.cost"
            id="startTime"
            placeholder="Cost"
            required
            autocomplete="off"
          />  
          
          <label for="service">Length in Minutes: </label>   
          <input class="flex-item"
            type="text"
            v-model="newService.lengthMinutes"
            id="endTime"
            placeholder="Length in Minutes"
            required
            autocomplete="off"
          />  
         <input type="submit" value="Add Service" class="flex-item btn btn-primary btn-sm" v-on:click="addService"/>
       
         </div>
      </form>
  
    </div>
</template>
  
<script>
    import servicesService from '../services/ServicesService.js';
    
    export default {
    name: 'ServicesList',
    data() {
        return {       
        servicesList: [],                      
        newService: {
            serviceId: 0,
            serviceName: '',
            description: '',
            cost: 0,
            lengthMinutes: 0
        }
        };
    },
    
    computed:{       
        services(){
            return this.$store.state.services;
        }
    },
    methods: {
        addService() {
        const service = {
            serviceName: this.newService.serviceName,
            description: this.newService.description,
            cost: this.newService.cost,
            lengthMinutes: this.newService.lengthMinutes,
            isEditing: false,
        };
        //Use service to create new service.
        servicesService.createService(service)
            .then(response => {
            if (response.status === 200 || response.status === 201) {
                // Get services and set services
                this.getAllServices();
                alert('Successfully Added Service');
                // Reset form
                this.resetForm();
            }
            })
            .catch(error => {
            if (error.response) {
                alert(`Error adding new service. Response received was "${error.response.statusText}".`, error);
            } else if (error.request) {                
                alert('Error adding new service. Server could not be reached.', error);
            } else {
                alert('Error adding new service. Request could not be created.', error);
            }
            }
            );
        },
        enableEdit(service) {
            // Save original values to allow canceling the edit
            service.originalServiceName = service.serviceName;
            service.originalDescription = service.description;
            service.originalCost = service.cost;
            service.originalLengthMinutes = service.lengthMinutes;
            service.isEditing = true;
        },
        saveEdit(service) {
        servicesService.updateService(service.serviceId, service)
            .then(response => {
                if (response.status === 200 || response.status === 201) {
                    // Get services and set services
                    this.getAllServices();
                }
            })
            .catch(error => {
                if (error.response) {
                    console.error(`Error updating service. Response received was "${error.response.statusText}".`, error);
                } else if (error.request) {                
                    console.error('Error updating service. Server could not be reached.', error);
                } else {
                    console.error('Error updating service(Might still be a success, Phone Validation API use limit has been reached). Request could not be created.', error);
                }
            })
            .finally(() => {
                service.isEditing = false;
                delete service.originalServiceName;
                delete service.originalDescription;
                delete service.originalCost;
                delete service.originalLengthMinutes;
            });
        },

        cancelEdit(service) {
            // Revert to original values and exit edit mode
            service.originalServiceName = service.serviceName;
            service.originalDescription = service.description;
            service.originalCost = service.cost;
            service.originalLengthMinutes = service.lengthMinutes;
            service.isEditing = false;
            delete service.originalServiceName;
            delete service.originalDescription;
            delete service.originalCost;
            delete service.originalLengthMinutes;
        },  
        getServices() {
          servicesService.getAllServices()
            .then(response => {
            this.servicesList = response.data;
            this.setServices(this.servicesList);
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
        setServices(services) {
            this.$store.commit('SET_SERVICES', services);
        },
        resetForm() {
            this.newService.originalServiceName = '';
            this.newService.originalDescription = '';
            this.newService.originalCost = 0;
            this.newService.originalLengthMinutes = 0;
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
    margin-bottom: 20px;
    width: 90vw;
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

</style>
  